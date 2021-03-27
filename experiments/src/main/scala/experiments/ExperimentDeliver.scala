package sgrub.experiments

import java.io.{File, PrintWriter}

import com.typesafe.scalalogging.Logger
import experiments.ExperimentTools
import io.reactivex.disposables.Disposable
import org.web3j.protocol.core.methods.response.TransactionReceipt
import sgrub.chain.{ChainDataOwner, ChainDataUser, ChainTools, StorageProviderChainListener}
import sgrub.inmemory.InMemoryStorageProvider

import scala.util.{Failure, Success, Try}

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


  def customGasLog(functionName: String, function: () => TransactionReceipt): Try[TransactionReceipt] = {
    val result = Try(function())
    result match {
      case Success(receipt) => {
        val gasCost = receipt.getGasUsed
        println(s"'$functionName' succeeded, gas used: $gasCost")

        // Keep track of the result.
        results = results :+ new ExperimentResult(currentKey * stepSize, gasCost)

        // Increment the key.
        currentKey += 1

        // The loop is finished.
        if(currentKey > length){

          // Store the results.
          val pw = new PrintWriter(new File(s"results/experiment7-$length-$stepSize.csv" ))
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

        result
      }
      case Failure(exception) => {
        println(s"'$functionName' failed, unable to measure gas. Exception: $exception")
        result
      }
    }
  }

  def startExperiment(): Unit = {
    // Initialise storage provider and data owner.
    val SP = new InMemoryStorageProvider
    val DO = new ChainDataOwner(SP, false, smAddress = smAddress)

    // Create the listener and listen to delivers.
    listener = new StorageProviderChainListener(SP, customGasLog, smAddress=smAddress, spAddress=spAddress).listen()

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