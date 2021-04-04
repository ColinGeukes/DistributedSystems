package experiments

import com.typesafe.scalalogging.Logger
import sgrub.experiments.ExperimentDeliver

import scala.io.StdIn

object ExperimentsStart {
  private val log = Logger(getClass.getName)

  def main(args: Array[String]): Unit = {


    print("Select one:" +
      "\n1: Experiment: Write X Bytes " +
      "\n2: Experiment: Write Batches with X Keys of 1 Byte" +
      "\n3: Experiment: Write X Bytes of Y Batches (even/random distributed)" +
      "\n4: Experiment: gGet cost with(out) replica" +
      "\n5: Experiment: Deliver cost" +
      "\n6: Experiment: Static Baselines" +
    "\nOption: ")
    StdIn.readInt() match {
      case 0 => {
        println("\nSample Experiment")
        SampleExperiment.run()
      }
      case 1 => {
        println("\nPut Single Batch Experiment")
        val byteSizes = stdin_int_array()

        println("\nSTART RUNNING WITHOUT REPLICATE")
        new ExperimentPutSingleBatch(byteSizes, false).startExperiment()


        println("\nSTART RUNNING WITH REPLICATE")
        new ExperimentPutSingleBatch(byteSizes, true).startExperiment()
      }
      case 2 => {
        println("\nPut Single Byte Multiple Keys Experiment")

        val sizes = stdin_int_array("Sizes (separated with ','): ")
        val amount = stdin_int_array("Times of size (separated with ','): ")

        if(sizes.length != amount.length){
          log.error("Arrays should have the same size")
          sys.exit(2)
        }

        println("\nSTART RUNNING WITH REPLICATE")
        new ExperimentPutBatch(sizes, amount, true).startExperiment()

        println("\nSTART RUNNING WITHOUT REPLICATE")
        new ExperimentPutBatch(sizes, amount, false).startExperiment()
      }
      case 3 => {
        println("\nPut Experiment")
        print("[1, X] bytes\nX: ")
        val xBytes = StdIn.readInt()
        print("StepSize: ")
        val xStepSize = StdIn.readInt()

        print("[1, Y] batches\nY: ")
        val yBatches = StdIn.readInt()
        print("StepSize: ")
        val yStepSize = StdIn.readInt()

        println("\nSTART RUNNING WITH EVEN DISTRIBUTED BYTE ARRAYS")
        new ExperimentPut(xBytes, xStepSize, yBatches, yStepSize, false).startExperiment()

        println("\nSTART RUNNING WITH EVEN RANDOM BYTE ARRAYS")
        new ExperimentPut(xBytes, xStepSize, yBatches, yStepSize, true).startExperiment()
      }

      case 4 => {
        println("\nGet Experiment")
        val byteSizes = stdin_int_array()
        print("Samples: ")
        val samples = StdIn.readInt()

        println("\nSTART RUNNING WITH REPLICATE")
        new ExperimentGet(byteSizes, samples, true).startExperiment()

        println("\nSTART RUNNING WITHOUT REPLICATE")
        new ExperimentGet(byteSizes, samples, false).startExperiment()
      }
      case 5 => {
        println("\nDeliver Experiment")
        val byteSizes = stdin_int_array()

        println("\nSTART RUNNING DELIVER EXPERIMENT")
        new ExperimentDeliver(byteSizes, 1).startExperiment()
      }
      case 6 => {
        println("\nStatic Baselines Experiment")
        print("Writes: ")
        val writes = StdIn.readInt()
        print("Reads: ")
        val reads = StdIn.readInt()

        println("\nSTART STATIC BASELINES DELIVER EXPERIMENT WITH REPLICATE")
        new ExperimentStaticBaselines(reads, writes, true).startExperiment()

        println("\nSTART STATIC BASELINES DELIVER EXPERIMENT NO REPLICATE")
        new ExperimentStaticBaselines(reads, writes, false).startExperiment()
      }


      case _ => {
        log.error("Enter a number between 1-5")
        sys.exit(1)
      }
    }

    sys.exit(0)
  }

  def stdin_int_array(question: String = "byte length array (separated with ','): "): Array[Int] = {
    print("byte length array (separated with ','): ")
    val byteString = StdIn.readLine()

    // Create the array from the string.
    byteString.split(",\\s*").map(_.toInt)
  }
}