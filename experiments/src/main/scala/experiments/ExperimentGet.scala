package experiments

import java.io.{File, PrintWriter}

import sgrub.chain.{ChainDataOwner, ChainDataUser}
import sgrub.experiments.BatchCreator
import sgrub.inmemory.InMemoryStorageProvider

class ExperimentGet(length: Int, stepSize: Int, replicate: Boolean) {


  // Create a new contract.
  private val (smAddress, spAddress) = ExperimentTools.deployContracts()

  // Objects.
  private val DU = new ChainDataUser(ExperimentTools.createGasLogCallback(getCallBack), smAddress=smAddress, spAddress=spAddress)

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
      val pw = new PrintWriter(new File(s"results/experiment-get-$length-$stepSize-$replicate.csv" ))
      results.foreach((element: ExperimentResult) => {
        element.write(pw)
      })
      pw.close()

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
    val DO = new ChainDataOwner(SP, replicate, ExperimentTools.createGasLogCallback((_: BigInt) => {
      // Call the get.
      DU.gGet(currentLength, (_, _) => {})
    }), smAddress=smAddress)

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