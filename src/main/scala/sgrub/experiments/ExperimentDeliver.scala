package sgrub.experiments

import java.io.{File, PrintWriter}

import com.typesafe.scalalogging.Logger
import sgrub.chain.{ChainDataOwner, ChainDataUser, ChainTools, StorageProviderChainListener}
import sgrub.console.BatchReader
import sgrub.inmemory.InMemoryStorageProvider

import scala.collection.mutable
import scala.io.StdIn

class ExperimentDeliver(length: Int, stepSize: Int) {
  private val log = Logger(getClass.getName)

  // Create a new contract.
  private val newContracts = ChainTools.deployContracts(false)
  private val smAddress = newContracts._1
  private val spAddress = newContracts._2

  // Objects.
  private val DU = new ChainDataUser(smAddress, spAddress)

  // The loop.
  private var running = true
  private var currentKey = 1

  // The results
  private var results = List() : List[ExperimentResult]

  def deliverCallBack(gasCost: BigInt): Unit = {
    // Keep track of the result.
    results = results :+ new ExperimentResult(currentKey * stepSize, gasCost)

    // Increment the key.
    currentKey += 1

    // The loop is finished.
    if(currentKey > length){

      // Stop the loop
      running = false

      // Store the results.
      val pw = new PrintWriter(new File(s"experiments/experiment7-$length-$stepSize.csv" ))
      results.foreach((element: ExperimentResult) => {
        element.write(pw)
      })
      pw.close
    }

    // Continue the loop by getting next value.
    else {
      DU.gGet(currentKey, (_, _) => {})
    }
  }

  def startExperiment(): Unit = {
    // Initialise storage provider and data owner.
    val SP = new InMemoryStorageProvider
    val DO = new ChainDataOwnerExperiment(SP, (_:BigInt) => {
      // Get the first entry.
      DU.gGet(currentKey, (_, _) => {})
    }, smAddress, false)

    // Create the listener and listen to delivers.
    val listener = new StorageProviderChainListener(SP, smAddress, spAddress, deliverCallBack).listen()

    // Put incremental length batch inside the contract.
    DO.gPuts(BatchCreator.createBatchIncremental(length, stepSize))


    // Keep the code running.
    while(running){}

    // Dispose the listener.
    listener.dispose()
  }

  class ExperimentResult(length: Int, gasCost: BigInt){
    def write(printWriter: PrintWriter): Unit ={
      printWriter.write(s"$length;$gasCost\n")
    }
  }
}

