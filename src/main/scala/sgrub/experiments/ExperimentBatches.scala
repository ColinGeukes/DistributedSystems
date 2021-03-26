package sgrub.experiments

import com.typesafe.scalalogging.Logger
import sgrub.inmemory.InMemoryStorageProvider

import scala.collection.mutable
import java.io._

class ExperimentBatches(maxBytes: Int, maxBatches: Int, rndDistribute: Boolean) {
  private val log = Logger(getClass.getName)

  private var currentBytes = 1
  private var currentBatches = 1

  val SP = new InMemoryStorageProvider
  val DO = new ChainDataOwnerExperiment(SP, callback, true)

  private var running = true

  private var results = List(): List[ExperimentResult]

  private def callback(gasUsed: BigInt): Unit = {
    // Store the results.
    results = results :+ new ExperimentResult(currentBytes, currentBatches, gasUsed)

    // We continue with the next experiment.
    currentBatches += 1
    if(currentBatches > maxBatches){
      currentBatches = 1
      currentBytes += 1
    }

    if(currentBytes > maxBytes) {
      running = false

      // Write results to file.
      val pw = new PrintWriter(new File(s"experiments/experiment5-$rndDistribute.csv" ))
      results.foreach((element: ExperimentResult) => {
        element.write(pw)
      })
      pw.close


    } else {
      startExperiment(currentBytes, currentBatches)
    }
  }

  def startExperiment(): Unit = {
    startExperiment(currentBytes, currentBatches)

    // Keep the thread active.
    while(running){}
  }

  private def startExperiment(bytes: Int, batches: Int): Unit = {
    log.info(s"Experiment $currentBytes bytes ${if (rndDistribute) "random" else "evenly"} distributed over $currentBatches batches.")

    // Inner experiment loop.
    DO.gPuts(createBatch(currentBytes, currentBatches))
  }

  def createBatch(bytes: Int, batches: Int): Map[Long, Array[Byte]] = {
    val result = mutable.Map.empty[Long, Array[Byte]]

    val rndKey = scala.util.Random.nextLong()

    // The normal distributed experiment.
    if(!rndDistribute){
      // For each key we insert a batch.
      for(batch <- 0 until batches){
        // Fill the key with a random batch array of size bytes. The byte corresponds to a readable char.
        result((rndKey + batch) % Long.MaxValue) = Array.fill(bytes)((scala.util.Random.nextInt(126-48) + 48).toByte)
      }
    }

    // The random distributed experiment.
    else {
      var bytesLeft = bytes * batches: Int
      for(batch <- 0 until batches - 1){

        // Retrieve random size and keep it inbounds.
        var currentBytes = scala.util.Random.nextInt(bytes * 2)
        if(currentBytes > bytesLeft){
          currentBytes = bytesLeft
        }

        result((rndKey + batch) % Long.MaxValue) = Array.fill(currentBytes)((scala.util.Random.nextInt(126-48) + 48).toByte)
        bytesLeft -= currentBytes
      }
      // Add the remainder to the last key.
      result((rndKey + batches - 1) % Long.MaxValue) = Array.fill(bytesLeft)((scala.util.Random.nextInt(126-48) + 48).toByte)
    }


    // Return the result.
    result.toMap
  }
}

private class ExperimentResult(bytes: Int, batches: Int, gasUsed: BigInt){
  def write(printWriter: PrintWriter): Unit ={
    printWriter.write(s"$bytes;$batches;$gasUsed\n")
  }
}