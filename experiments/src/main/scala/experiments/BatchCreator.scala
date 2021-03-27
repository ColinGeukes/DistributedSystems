package sgrub.experiments

import scala.collection.mutable

object BatchCreator {
  def createBatchIncremental(maxLength: Int, stepSize: Int): Map[Long, Array[Byte]] = {
    val result = mutable.Map.empty[Long, Array[Byte]]

    // For each key we insert a batch.
    for(currLength <- 1 to maxLength){

      // Fill the key with a random batch array of size bytes. The byte corresponds to a readable char.
      result(currLength) = Array.fill(currLength * stepSize)((scala.util.Random.nextInt(90 - 56) + 56).toByte)
    }

    // Return the result.
    result.toMap
  }

  def createBatchEqualBytes(steps: Int, bytes: Int): Map[Long, Array[Byte]] = {
    val result = mutable.Map.empty[Long, Array[Byte]]

    // For each key we insert a batch.
    for(currLength <- 1 to steps){

      // Fill the key with a random batch array of size bytes. The byte corresponds to a readable char.
      result(currLength) = Array.fill(bytes)((scala.util.Random.nextInt(90 - 56) + 56).toByte)
    }

    // Return the result.
    result.toMap
  }
}