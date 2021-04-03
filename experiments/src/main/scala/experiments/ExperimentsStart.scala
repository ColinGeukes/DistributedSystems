package experiments

import com.typesafe.scalalogging.Logger
import sgrub.experiments.ExperimentDeliver

import scala.io.StdIn

object ExperimentsStart {
  private val log = Logger(getClass.getName)

  def main(args: Array[String]): Unit = {


    print("Select one:" +
      "\n1: Experiment: Write X Bytes " +
      "\n2: Experiment: Write X Bytes of Y Batches (even/random distributed)" +
      "\n3: Experiment: gGet cost with(out) replica" +
      "\n4: Experiment: Deliver cost" +
      "\n5: Experiment: Static Baselines" +
    "\nOption: ")
    StdIn.readInt() match {
      case 0 => {
        println("\nSample Experiment")
        SampleExperiment.run()
      }
      case 1 => {
        println("\nPut Single Batch Experiment")
        print("byte length array (separated with ','): ")
        val byteString = StdIn.readLine()
        val byteSizes = byteString.split(",\\s*").map(_.toInt)
        println(s"Array[${byteSizes.mkString(",")}]")

        println("\nSTART RUNNING WITHOUT REPLICATE")
        new ExperimentPutSingleBatch(byteSizes, false).startExperiment()


        println("\nSTART RUNNING WITH REPLICATE")
        new ExperimentPutSingleBatch(byteSizes, true).startExperiment()
      }
      case 2 => {
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
      case 3 => {
        println("\nGet Experiment")
        print("Length: ")
        val length = StdIn.readInt()
        print("StepSize: ")
        val stepSize = StdIn.readInt()

        println("\nSTART RUNNING WITHOUT REPLICATE")
        new ExperimentGet(length, stepSize, false).startExperiment()

        println("\nSTART RUNNING WITH REPLICATE")
        new ExperimentGet(length, stepSize, true).startExperiment()
      }
      case 4 => {
        println("\nDeliver Experiment")
        print("Length: ")
        val length = StdIn.readInt()
        print("StepSize: ")
        val stepSize = StdIn.readInt()

        println("\nSTART RUNNING DELIVER EXPERIMENT")
        new ExperimentDeliver(length, stepSize).startExperiment()
      }
      case 5 => {
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
}