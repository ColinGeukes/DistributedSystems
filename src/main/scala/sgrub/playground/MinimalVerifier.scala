package sgrub.playground

import com.google.common.primitives.Ints
import org.bouncycastle.crypto.digests.KeccakDigest

// Implements a minimal verifier for a single Lookup that doesn't rely on scrypto
// When calling, make sure to remove the Tag from the digest...
class MinimalVerifier(latestDigest: Array[Byte]) {
  val keyLength = 8
  val digestSize = 32

  // We'll have to figure out how to do this, too
  def hash(toHash: Array[Byte]): Array[Byte] = {
    val digestFn = new KeccakDigest(256)
    val retValue = new Array[Byte](32)
    digestFn.update(toHash, 0, toHash.length)
    digestFn.doFinal(retValue, 0)
    retValue
  }

  def prefixedHash(prefix: Byte, toHash: Array[Byte]*): Array[Byte] = {
    // String all inputs together
    hash(prefix +: toHash.flatMap(_.toList).toArray)
  }

  // This trait already exists in the package, I'm just copying it here to keep track of what's actually happening
  // Note that Byte acts as int8
  // Do not use bytes -1, 0, or 1 -- these are for balance
  val LeafInPackagedProof: Byte = 2
  val LabelInPackagedProof: Byte = 3
  val EndOfTreeInPackagedProof: Byte = 4

  // Callback contains the value
  def manualVerify(key: Array[Byte], proof: Array[Byte], callback: Option[Array[Byte]] => Unit): Boolean = {
    // Make sure this is Digest32
    if (latestDigest.length == digestSize) {
      val labelLength = digestSize
      // Convert Byte to Int
      val rootNodeHeight = latestDigest(31) & 0xFF
      var value: Option[Array[Byte]] = None
      val maxNodes = 2 * rootNodeHeight + 2
      var numNodes = 0
      var s = List.empty[Array[Byte]] // Nodes and depths
      var i = 0
      while (proof(i) != EndOfTreeInPackagedProof) {
        val n = proof(i)
        i += 1
        numNodes += 1

        if (numNodes > maxNodes) {
          println("Proof too long")
          return false
        }

        if (n == LabelInPackagedProof) {
          val label = proof.slice(i, i + labelLength)
          i += labelLength
          s = label +: s
        }
        else if (n == LeafInPackagedProof) {
          // There should only be one of these...
          if (value.nonEmpty)
            return false
          val leafKey = {
            val start = i
            i += keyLength
            proof.slice(start, i)
          }
          if (!key.sameElements(leafKey)) {
            println("Key doesn't match")
            return false
          }
          val nextLeafKey = proof.slice(i, i + keyLength)
          i += keyLength
          val valueLength = Ints.fromByteArray(proof.slice(i, i + 4))
          i += 4
          value = Some(proof.slice(i, i + valueLength))
          i += valueLength
          val label = prefixedHash(0: Byte, leafKey, value.get, nextLeafKey)
          s = label +: s
        }
        else {
          // Grab the first two labels as left and right (Removing them from the list)
          val right = s.head
          s = s.tail
          val left = s.head
          s = s.tail
          // Then make the new label the head of the list
          val label = prefixedHash(1: Byte, Array(n), left, right)
          s = label +: s
        }
      }
      if (s.size == 1) {
        val root = s.head
        if (latestDigest.sameElements(root)) {
          callback(value)
          return true
        } else {
          println("Digest doesn't match")
        }
      } else {
        println("Size wrong?")
      }
    } else {
      println("Incorrect Digest size")
    }
    false
  }

}
