package sgrub.inmemory

import com.google.common.primitives.Longs
import scorex.crypto.authds.avltree.batch.{BatchAVLVerifier, Lookup}
import scorex.crypto.authds.{ADDigest, ADKey, SerializedAdProof}
import sgrub.contracts.{DataUser, DigestType, HashFunction, KeyLength, StorageProvider}

import scala.collection.mutable
import scala.util.{Failure, Success}

/**
 * Represents the Data User, stores data in memory and has a direct connection to the SP object, instead of via the blockchain
 * @param sp The storage provider
 */
class InMemoryDataUser(
  sp: StorageProvider
) extends DataUser {
  private val _kvReplicas = mutable.Map.empty[Long, Array[Byte]]
  private var _latestDigest: ADDigest = ADDigest @@ Array.empty[Byte]

  override def kvReplicas: Map[Long, Array[Byte]] = _kvReplicas.toMap

  override def latestDigest: ADDigest = _latestDigest

  override def update(kvs: Map[Long, Array[Byte]], newDigest: ADDigest): Unit = {
    // Needs logic to determine whether to replicate/delete KVs based on key, ignoring that part for now
    kvs.foreach(kv => _kvReplicas(kv._1) = kv._2)
    _latestDigest = newDigest
  }

  override def deliver(
    key: Long,
    replicate: Boolean,
    proof: SerializedAdProof,
    callback: (Long, Array[Byte]) => Unit): Unit = {
    val verifier = new BatchAVLVerifier[DigestType, HashFunction](
      latestDigest,
      proof,
      keyLength = KeyLength,
      valueLengthOpt = None,
      maxNumOperations = Some(1),
      maxDeletes = Some(0)
    )
    verifier.performOneOperation(Lookup(ADKey @@ Longs.toByteArray(key))) match {
      case Success(successResult) => successResult match {
        case Some(existResult) => {
          if (replicate) _kvReplicas(key) = existResult
          callback(key, existResult)
        }
        case _ => println(s"Fail. No value for key: $key")
      }
      case Failure(exception) => println(s"Fail. $exception")
    }
  }

  override def request(key: Long, deliver: (Boolean, SerializedAdProof) => Unit): Unit =  {
    // This would normally be done over the blockchain, for now we use a direct connection with the SP
    sp.request(key, proof => deliver(false, proof))
  }
}
