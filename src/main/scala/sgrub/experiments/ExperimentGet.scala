package sgrub.experiments

import java.io.{File, PrintWriter}

import sgrub.inmemory.InMemoryStorageProvider

import scala.collection.mutable

class ExperimentGet(length: Int, stepSize: Int, replicate: Boolean) {
  private var running = true
  private val DU = new ChainDataUserExperiment(getCallBack)
  private var currentLength = 1
  private var results = List() : List[ExperimentResult]


  def getCallBack(gasCost: BigInt): Unit = {
    // Keep track of the result.
    results = results :+ new ExperimentResult(currentLength * stepSize, gasCost)

    // Next get.
    currentLength += 1

    // Check if inbounds.
    if(currentLength > length){

      // Store the results.
      val pw = new PrintWriter(new File(s"experiments/experiment6-$replicate.csv" ))
      results.foreach((element: ExperimentResult) => {
        element.write(pw)
      })
      pw.close

      // Stop the loop.
      running = false
    }

    // We are still running, so perform next get.
    else {
      DU.gGet(currentLength, (_, _) => {})
    }
  }


  def startExperiment(): Unit = {
    val SP = new InMemoryStorageProvider
    val DO = new ChainDataOwnerExperiment(SP, (_: BigInt) => {
      // Call the get.
      DU.gGet(currentLength, (_, _) => {})
    }, replicate)
    DO.gPuts(createBatch(length))

    // Keep the code running.
    while(running){}
  }

  def createBatch(maxLength: Int): Map[Long, Array[Byte]] = {
    val result = mutable.Map.empty[Long, Array[Byte]]

    // For each key we insert a batch.
    for(currLength <- 1 to maxLength){

      // Fill the key with a random batch array of size bytes. The byte corresponds to a readable char.
      result(currLength) = Array.fill(currLength * stepSize)((scala.util.Random.nextInt(90 - 56) + 56).toByte)
    }

    // Return the result.
    result.toMap
  }

  class ExperimentResult(length: Int, gasCost: BigInt){
    def write(printWriter: PrintWriter): Unit ={
      printWriter.write(s"$length;$gasCost\n")
    }
  }
}

