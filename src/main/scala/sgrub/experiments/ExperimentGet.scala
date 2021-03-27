package sgrub.experiments

import java.io.{File, PrintWriter}

import sgrub.chain.ChainTools
import sgrub.inmemory.InMemoryStorageProvider

import scala.collection.mutable

class ExperimentGet(length: Int, stepSize: Int, replicate: Boolean) {


  // Create a new contract.
  private val newContracts = ChainTools.deployContracts(false)
  private val smAddress = newContracts._1
  private val spAddress = newContracts._2

  // Objects.
  private val DU = new ChainDataUserExperiment(smAddress, spAddress, getCallBack)

  // The loop.
  private var running = true
  private var currentLength = 1

  // The results
  private var results = List() : List[ExperimentResult]


  def getCallBack(gasCost: BigInt): Unit = {
    // Keep track of the result.
    results = results :+ new ExperimentResult(currentLength * stepSize, gasCost)

    // Next get.
    currentLength += 1

    // Check if inbounds.
    if(currentLength > length){

      // Store the results.
      val pw = new PrintWriter(new File(s"experiments/experiment6-$length-$stepSize-$replicate.csv" ))
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
    }, smAddress, replicate)
    DO.gPuts(BatchCreator.createBatchIncremental(length, stepSize))

    // Keep the code running.
    while(running){}
  }

  class ExperimentResult(length: Int, gasCost: BigInt){
    def write(printWriter: PrintWriter): Unit ={
      printWriter.write(s"$length;$gasCost\n")
    }
  }
}

