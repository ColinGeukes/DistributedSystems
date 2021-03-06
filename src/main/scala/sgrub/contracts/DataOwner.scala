package sgrub.contracts

import sgrub.inmemory.InMemoryStorageProvider

/**
 * Represents the Data Owner producing a stream of data updates
 */
trait DataOwner {

  def keyLength: Int = 8
  def storageProvider: StorageProvider

  /**
   * Runs the ADS protocol with [[storageProvider]]
   *
   * @param kvs The key-value pairs to be updated
   * @return True when the KVs were updated successfully and securely, otherwise returns False
   */
  def gPuts(kvs: Map[Long, Array[Byte]]): Boolean
}
