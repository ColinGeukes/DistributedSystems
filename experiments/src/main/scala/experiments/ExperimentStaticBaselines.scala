package experiments

import java.io.{File, PrintWriter}

import sgrub.chain.{ChainDataOwner, ChainDataUser}
import sgrub.experiments.BatchCreator
import sgrub.inmemory.InMemoryStorageProvider

class ExperimentStaticBaselines(reads: Int, writes: Int, replicate: Boolean) {

  // Create a new contract.
  private val newContracts = ExperimentTools.deployContracts()
  private val smAddress = newContracts._1
  private val spAddress = newContracts._2

  // Objects.
  private val DU = new ChainDataUser(ExperimentTools.createGasLogCallback(getCallBack), smAddress=smAddress, spAddress=spAddress)

  // The loop.
  private var running = true
  private var currentReads = 0
  private var currentWrites = 0
  private var currentOperations = 0
  private var totalGasCost = 0 : BigInt

  // The results
  private var results = List() : List[ExperimentResult]


  def getCallBack(gasCost: BigInt): Unit = {

    // Next get.
    currentOperations +=1
    currentReads += 1
    totalGasCost += gasCost

    // Keep track of the result.
    results = results :+ new ExperimentResult(currentOperations, currentReads, currentWrites, gasCost, totalGasCost)

    // Check if inbounds.
    if(currentReads >= reads){

      // Store the results.
      val pw = new PrintWriter(new File(s"results/experiment-baselines-$reads-$writes-$replicate.csv" ))
      results.foreach((element: ExperimentResult) => {
        element.write(pw)
      })
      pw.close()

      // Stop the loop.
      running = false
    }

    // We are still running, so perform next get.
    else {
      DU.gGet(1 + scala.util.Random.nextInt(writes), (_, _) => {})
    }
  }


  def startExperiment(): Unit = {
    val SP = new InMemoryStorageProvider
    val DO = new ChainDataOwner(SP, replicate, ExperimentTools.createGasLogCallback((gasCost: BigInt) => {
      currentOperations += 1
      currentWrites += writes
      totalGasCost += gasCost

      // Save the result of the write.
      results = List(new ExperimentResult(currentOperations, currentReads, currentWrites, gasCost, totalGasCost))

      // Call the get.
      DU.gGet(1 + scala.util.Random.nextInt(writes), (_, _) => {})
    }), smAddress=smAddress)

    DO.gPuts(BatchCreator.createBatchEqualBytes(writes, 1))

    // Keep the code running.
    while(running){}
  }

  private class ExperimentResult(operations: Int, currReads: Int, currWrites: Int, gasUsed: BigInt, gasUsedTotal: BigInt){
    def write(printWriter: PrintWriter): Unit ={
      printWriter.write(s"$operations;$currReads;$currWrites;$gasUsed;$gasUsedTotal\n")
    }
  }
}