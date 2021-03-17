package sgrub.chain

import com.google.common.primitives.Longs
import com.typesafe.scalalogging.Logger
import scorex.crypto.authds.{ADDigest, ADKey, ADValue, EmptyByteArray}
import scorex.crypto.authds.avltree.batch.{BatchAVLVerifier, InsertOrUpdate}
import sgrub.contracts.{DataOwner, DataUser, DigestType, HashFunction, KeyLength, StorageProvider, hf}
import sgrub.smartcontracts.generated.StorageManager

import scala.collection.JavaConverters._

class ChainDataOwner(
  sp: StorageProvider,
  sm: StorageManager
) extends DataOwner {
  private val log = Logger(getClass.getName)

  private def storageProvider: StorageProvider = sp
  private var _latestDigest: ADDigest = storageProvider.initialDigest
  override def latestDigest: ADDigest = _latestDigest

  /**
   * Runs the ADS protocol with [[storageProvider]]
   *
   * @param kvs The key-value pairs to be updated
   * @return True when the KVs were updated successfully and securely, otherwise returns False
   */
  override def gPuts(kvs: Map[Long, Array[Byte]]): Boolean = {
    log.info(s"gPuts: ${kvs.toString()}")
    val (receivedDigest, receivedProof) = storageProvider.gPuts(kvs)
    val ops = kvs.map(kv => InsertOrUpdate(ADKey @@ Longs.toByteArray(kv._1), ADValue @@ kv._2))
    val verifier = new BatchAVLVerifier[DigestType, HashFunction](
      _latestDigest,
      receivedProof,
      keyLength = KeyLength,
      valueLengthOpt = None,
      maxNumOperations = Some(ops.size),
      maxDeletes = Some(0)
    )(hf)

    ops.foreach(verifier.performOneOperation)

    verifier.digest match {
      case Some(digest) if digest.sameElements(receivedDigest) => {
        _latestDigest = receivedDigest
        // No replication logic yet
        log.info(s"Updating digest, new digest: $receivedDigest")
        //sm.update(List(Array.ofDim[Byte](8)).asJava, List(Array.ofDim[Byte](8)).asJava, _latestDigest.slice(0, 32)).send()
        sm.update(kvs.keys.map(Longs.toByteArray).toList.asJava, kvs.values.toList.asJava, _latestDigest.slice(0, 32)).send()
        true
      }
      case _ => false
    }
  }
}
