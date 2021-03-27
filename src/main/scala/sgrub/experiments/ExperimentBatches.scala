package sgrub.experiments

import com.typesafe.scalalogging.Logger
import sgrub.inmemory.InMemoryStorageProvider

import scala.collection.mutable
import java.io._

import sgrub.chain.ChainTools

class ExperimentBatches(maxBytes: Int, byteStepSize: Int, maxBatches: Int, batchStepSize: Int, rndDistribute: Boolean) {
  private val log = Logger(getClass.getName)

  // Create a new contract.
  private val newContracts = ChainTools.deployContracts(false)
  private val smAddress = newContracts._1

  // Objects
  val SP = new InMemoryStorageProvider
  val DO = new ChainDataOwnerExperiment(SP, callback, smAddress, true)

  // Looping
  private var currentBytes = 1
  private var currentBatches = 1
  private var currentKey = 1L
  private var running = true

  // Results
  private var results = List(): List[ExperimentResult]

  private def callback(gasUsed: BigInt): Unit = {
    // Store the results.
    results = results :+ new ExperimentResult(currentBytes * byteStepSize, currentBatches * batchStepSize, gasUsed)

    // We continue with the next experiment.
    currentBatches += 1
    if(currentBatches > maxBatches){
      currentBatches = 1
      currentBytes += 1
    }

    if(currentBytes > maxBytes) {
      // Stop the loop.
      running = false

      // Write results to file.
      val pw = new PrintWriter(new File(s"experiments/experiment5-$maxBytes-$byteStepSize-$maxBatches-$batchStepSize-$rndDistribute.csv" ))
      results.foreach((element: ExperimentResult) => {
        element.write(pw)
      })
      pw.close
    }

    // Continue with the experiments.
    else {
      startExperiment(currentBytes, currentBatches)
    }
  }

  def startExperiment(): Unit = {
    startExperiment(currentBytes, currentBatches)

    // Keep the thread active.
    while(running){}
  }

  private def startExperiment(bytes: Int, batches: Int): Unit = {
    val bytesTot = currentBytes * byteStepSize
    val batchesTot = currentBatches * batchStepSize

    log.info(s"Experiment $bytesTot bytes ${if (rndDistribute) "random" else "evenly"} distributed over $batchesTot batches.")

    // Inner experiment loop.
    DO.gPuts(createBatch(bytesTot, batchesTot))
  }

  def createBatch(bytes: Int, batches: Int): Map[Long, Array[Byte]] = {
    val result = mutable.Map.empty[Long, Array[Byte]]

    // The normal distributed experiment.
    if(!rndDistribute){
      // For each key we insert a batch.
      for(batch <- 0 until batches){
        // Fill the key with a random batch array of size bytes. The byte corresponds to a readable char.
        result(currentKey % Long.MaxValue) = Array.fill(bytes)((scala.util.Random.nextInt(90-56) + 56).toByte)
        currentKey += 1
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

        result(currentKey % Long.MaxValue) = Array.fill(currentBytes)((scala.util.Random.nextInt(90-56) + 56).toByte)
        currentKey += 1
        bytesLeft -= currentBytes
      }
      // Add the remainder to the last key.
      result(currentKey % Long.MaxValue) = Array.fill(bytesLeft)((scala.util.Random.nextInt(90-56) + 56).toByte)
      currentKey += 1
    }


    // Return the result.
    result.toMap
  }

  private class ExperimentResult(bytes: Int, batches: Int, gasUsed: BigInt){
    def write(printWriter: PrintWriter): Unit ={
      printWriter.write(s"$bytes;$batches;$gasUsed\n")
    }
  }
}

