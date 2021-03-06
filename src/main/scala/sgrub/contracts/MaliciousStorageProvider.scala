package sgrub.contracts

import java.nio.charset.StandardCharsets

import com.google.common.primitives.{Bytes, Longs}
import scorex.crypto.authds.avltree.batch._
import scorex.crypto.hash.{Blake2b256, Digest32}
import scorex.crypto.authds.{ADDigest, ADKey, ADValue, SerializedAdProof}

class MaliciousStorageProvider extends StorageProvider {
  // This currently acts as our KV Store
  private val prover = new BatchAVLProver[Digest32, Blake2b256.type](8, valueLengthOpt = None)
  override val initialDigest: ADDigest = prover.digest

  override def gPuts(kvs: Map[Long, Array[Byte]]): (ADDigest, SerializedAdProof) = {
    val ops = kvs.map(kv => InsertOrUpdate(ADKey @@ Longs.toByteArray(kv._1),
      ADValue @@ "Haxxorz".getBytes(StandardCharsets.UTF_8)))
    ops.foreach(prover.performOneOperation)
    (prover.digest, prover.generateProof())
  }

  override def gGet(key: Long, callback: (Long, Array[Byte]) => Unit): Unit = {
    prover.unauthenticatedLookup(ADKey @@ Longs.toByteArray(key)) match {
      case Some(result) => callback(key, result)
      case _ => // Request it
    }
  }
}
