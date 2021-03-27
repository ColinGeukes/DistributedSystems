package sgrub.experiments

import java.io.{File, PrintWriter}

import com.typesafe.scalalogging.Logger
import experiments.ExperimentTools
import io.reactivex.disposables.Disposable
import sgrub.chain.{ChainDataOwner, ChainDataUser, StorageProviderChainListener}
import sgrub.inmemory.InMemoryStorageProvider

class ExperimentDeliver(length: Int, stepSize: Int) {
  private val log = Logger(getClass.getName)

  // Create a new contract.
  private val newContracts = ExperimentTools.deployContracts()
  private val smAddress = newContracts._1
  private val spAddress = newContracts._2

  // Objects.
  private val DU = new ChainDataUser(smAddress=smAddress, spAddress=spAddress)
  private var listener = null: Disposable

  // The loop.
  private var running = true
  private var currentKey = 1

  // The results
  private var results = List() : List[ExperimentResult]

  def deliverCallBack(gasCost: BigInt): Unit ={

    // Keep track of the result.
    results = results :+ new ExperimentResult(currentKey * stepSize, gasCost)

    // Increment the key.
    currentKey += 1

    // The loop is finished.
    if(currentKey > length){

      // Store the results.
      val pw = new PrintWriter(new File(s"results/experiment-deliver-$length-$stepSize.csv" ))
      results.foreach((element: ExperimentResult) => {
        element.write(pw)
      })
      pw.close

      // Dispose the listener.
      log.info("Dispose the listener")
      listener.dispose()

      // Stop the loop
      running = false
    }

    // Continue the loop by getting next value.
    else {
      DU.gGet(currentKey, (_, _) => {})
    }
  }

  def startExperiment(): Unit = {
    // Initialise storage provider and data owner.
    val SP = new InMemoryStorageProvider
    val DO = new ChainDataOwner(SP, false, ExperimentTools.createGasLogCallback((_) => {
      DU.gGet(currentKey, (_, _) => {})
    }), smAddress = smAddress)

    // Create the listener and listen to delivers.
    listener = new StorageProviderChainListener(SP, ExperimentTools.createGasLogCallback(deliverCallBack), smAddress=smAddress, spAddress=spAddress).listen()

    // Put incremental length batch inside the contract.
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