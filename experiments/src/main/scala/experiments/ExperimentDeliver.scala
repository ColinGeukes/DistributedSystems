package sgrub.experiments

import java.io.{File, PrintWriter}

import com.typesafe.scalalogging.Logger
import experiments.ExperimentTools
import io.reactivex.disposables.Disposable
import sgrub.chain.{ChainDataOwner, ChainDataUser, StorageProviderChainListener}
import sgrub.inmemory.InMemoryStorageProvider

import scala.collection.mutable

class ExperimentDeliver(bytes: Array[Int]) {
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
  private var firstInput = true
  private var currentKey = 0

  // The results
  private var results = List() : List[ExperimentResult]

  def deliverCallBack(gasCost: BigInt): Unit ={


    // Increment the key.
    if(!firstInput){
      results = results :+ new ExperimentResult(currentKey, bytes(currentKey), gasCost)
      currentKey += 1

      // Store the experiment per step, if something goes wrong.
      val pw = new PrintWriter(new File(s"results/experiment-deliver-${bytes.mkString("_")}.csv" ))
      results.foreach((element: ExperimentResult) => {
        element.write(pw)
      })
      pw.close()

    } else {
      firstInput = false
    }

    // The loop is finished.
    if(currentKey >= bytes.length){

      // Dispose the listener.
      log.info("Dispose the listener")
      listener.dispose()

      // Stop the loop
      running = false
    }

    // Continue the loop by getting next value.
    else {
      DU.gGet(currentKey + 1, (_, _) => {})
    }
  }



  def startExperiment(): Unit = {
    // Initialise storage provider and data owner.
    val SP = new InMemoryStorageProvider
    val DO = new ChainDataOwner(SP, false, ExperimentTools.createGasLogCallback((_) => {
      DU.gGet(1, (_, _) => {})
    }), smAddress = smAddress)

    // Create the listener and listen to delivers.
    listener = new StorageProviderChainListener(SP, ExperimentTools.createGasLogCallback(deliverCallBack), smAddress=smAddress, spAddress=spAddress).listen()

    // Put incremental length batch inside the contract.
    DO.gPuts(createBatch())


    // Keep the code running.
    while(running){}
  }

  def createBatch(): Map[Long, Array[Byte]] = {
    val result = mutable.Map.empty[Long, Array[Byte]]

    // Insert each key in the batch
    for(key <- 0 until bytes.length){
      // Fill the key with a random batch array of size bytes. The byte corresponds to a readable char.
      result(key + 1) = Array.fill(bytes(key))((scala.util.Random.nextInt(90-56) + 56).toByte)
    }

    // Return the result.
    result.toMap
  }

  class ExperimentResult(key: Int, bytes: Int, gasCost: BigInt){
    def write(printWriter: PrintWriter): Unit ={
      printWriter.write(s"$key;$bytes;$gasCost\n")
    }
  }
}