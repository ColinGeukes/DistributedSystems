package sgrub.contracts

import com.google.common.primitives.Longs
import scorex.crypto.authds.{ADDigest, ADKey, ADValue, SerializedAdProof}
import scorex.crypto.authds.avltree.batch._
import scorex.crypto.hash.{Blake2b256, Digest32}

class StorageProvider {
  // This currently acts as our KV Store
  private val prover = new BatchAVLProver[Digest32, Blake2b256.type](8, valueLengthOpt = None)
  val initialDigest: ADDigest = prover.digest

  def gPuts(kvs: Map[Long, Array[Byte]]): (ADDigest, SerializedAdProof) = {
    val ops = kvs.map(kv => InsertOrUpdate(ADKey @@ Longs.toByteArray(kv._1), ADValue @@ kv._2))
    ops.foreach(prover.performOneOperation)
    (prover.digest, prover.generateProof())
  }

  def gGet(key: Long, callback: (Long, Array[Byte]) => Unit): Unit = {
    prover.unauthenticatedLookup(ADKey @@ Longs.toByteArray(key)) match {
      case Some(result) => callback(key, result)
      case _ => // Request it
    }
  }
}
