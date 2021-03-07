package sgrub.contracts

import scorex.crypto.authds.{ADDigest, SerializedAdProof}

/**
 * Represents the Data User consuming data updates by key (should be a smart contract?)
 */
trait DataUser {
  /**
   * @return Local replicas of key-value pairs
   */
  def kvReplicas: Map[Long, Array[Byte]]

  /**
   * @return Latest digest received from DO
   */
  def latestDigest: ADDigest

  final def gGet(key: Long, callback: (Long, Array[Byte]) => Unit) = {
    kvReplicas.get(key) match {
      case Some(value) => callback(key, value)
      case _ => request(key, (replicate, proof) => deliver(key, replicate, proof, callback))
    }
  }

  /**
   * Called by the DO to update the digest and any key-value pairs to replicate/delete
   * @param kvs Key-value pairs to replicate/delete
   * @param newDigest Latest digest
   */
  def update(kvs: Map[Long, Array[Byte]], newDigest: ADDigest): Unit

  /**
   * Function (indirectly) called by the Storage Provider to return a key-value pair that wasn't replicated before
   * @param key Key which was called originally in [[gGet]]
   * @param replicate Instruction to replicate the value or not
   * @param proof Proof from the Storage Provider
   * @param callback The original query on the key-value pair
   */
  def deliver(key: Long, replicate: Boolean, proof: SerializedAdProof, callback: (Long, Array[Byte]) => Unit): Unit

  /**
   * Sends an event to be picked up by the SP to deliver a non-replicated key-value pair
   * @param key
   * @param deliver
   */
  def request(key: Long, deliver: (Boolean, SerializedAdProof) => Unit): Unit
}
