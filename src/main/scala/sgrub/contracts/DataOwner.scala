package sgrub.contracts

import scorex.crypto.authds.ADDigest
import sgrub.inmemory.InMemoryStorageProvider

/**
 * Represents the Data Owner producing a stream of data updates
 */
trait DataOwner {
  def storageProvider: StorageProvider
  def latestDigest: ADDigest

  /**
   * Registers a data user to update with the latest digests and replication changes
   * @param user The DU to update
   */
  def register(user: DataUser): Unit

  /**
   * Runs the ADS protocol with [[storageProvider]]
   *
   * @param kvs The key-value pairs to be updated
   * @return True when the KVs were updated successfully and securely, otherwise returns False
   */
  def gPuts(kvs: Map[Long, Array[Byte]]): Boolean
}
