package sgrub

import com.typesafe.scalalogging.Logger
import sgrub.chain.{ChainDataOwner, ChainDataUser, ChainTools, StorageProviderChainListener}
import sgrub.console.BatchReader
import sgrub.experiments.{ExperimentBatches, ExperimentGet}
import sgrub.inmemory.InMemoryStorageProvider
import sgrub.playground.ScryptoInMemoryThings

import scala.io.StdIn

object SgrubStart {
  private val log = Logger(getClass.getName)

  def main(args: Array[String]): Unit = {
    if (!config.isResolved) {
      log.error("Unable to resolve config")
      sys.exit(1)
    }
    println("Select one:" +
      "\n1: Deploy Smart Contracts" +
      "\n2: Start DataOwner and StorageProvider" +
      "\n3: Start DataUser" +
      "\n4: Demo: In-memory ADS" +
      "\n5: Experiment: X Bytes of Y Batches (even/random distributed)" +
      "\n6: Experiment: gGet cost with(out) replica")
    StdIn.readInt() match {
      case 1 => ChainTools.deployContracts()
      case 2 => {
        println("Have DO replicate? (y/n)")
        val replicate = StdIn.readBoolean()
        val SP = new InMemoryStorageProvider
        val DO = new ChainDataOwner(SP, replicate)
        val listener = new StorageProviderChainListener(SP).listen()
        do {
          val batch = BatchReader.readBatch()
          DO.gPuts(batch)
        } while ({
          println("Enter a new batch? (y/n) -- Don't answer to continue listening.")
          StdIn.readBoolean()
        })
        listener.dispose()
      }
      case 3 => {
        val DU = new ChainDataUser
        do {
          println("Enter a key to gGet:")
          DU.gGet(StdIn.readLong(), (key, value) => {
            println(s"Received a value. Key: $key, Value: ${new String(value)}")
          })
        } while ({
          println("gGet a new value? (y/n) -- Don't answer to continue waiting.")
          StdIn.readBoolean()
        })
      }
      case 4 => ScryptoInMemoryThings.tryInMemoryGrub()
      case 5 => {
        print("[1, X] bytes\nX: ")
        val xBytes = StdIn.readInt()
        print("StepSize: ")
        val xStepSize = StdIn.readInt()

        print("[1, Y] batches\nY: ")
        val yBatches = StdIn.readInt()
        print("StepSize: ")
        val yStepSize = StdIn.readInt()

        println("\nSTART RUNNING WITH EVEN DISTRIBUTED BYTE ARRAYS")
        new ExperimentBatches(xBytes, xStepSize, yBatches, yStepSize, false).startExperiment()

        println("\nSTART RUNNING WITH EVEN RANDOM BYTE ARRAYS")
        new ExperimentBatches(xBytes, xStepSize, yBatches, yStepSize, true).startExperiment()
      }
      case 6 => {
        print("Sizes: ")
        val sizes = StdIn.readInt()
        print("StepSize: ")
        val stepSize = StdIn.readInt()

        println("\nSTART RUNNING WITHOUT REPLICATE")
        new ExperimentGet(sizes, stepSize, false).startExperiment()

        println("\nSTART RUNNING WITH REPLICATE")
        new ExperimentGet(sizes, stepSize, true).startExperiment()
      }
      case _ => {
        log.error("Enter a number between 1-5")
        sys.exit(1)
      }
    }
    sys.exit(0)
  }
}
