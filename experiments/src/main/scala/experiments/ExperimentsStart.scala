package experiments

import com.typesafe.scalalogging.Logger
import sgrub.experiments.ExperimentDeliver

import scala.io.StdIn

object ExperimentsStart {
  private val log = Logger(getClass.getName)

  def main(args: Array[String]): Unit = {


    println("Select one:" +
      "\n1: Experiment: X Bytes of Y Batches (even/random distributed)" +
      "\n2: Experiment: gGet cost with(out) replica" +
      "\n3: Experiment: Deliver cost")
    StdIn.readInt() match {
      case 0 => {
        SampleExperiment.run()
      }
      case 1 => {
        print("[1, X] bytes\nX: ")
        val xBytes = StdIn.readInt()
        print("StepSize: ")
        val xStepSize = StdIn.readInt()

        print("[1, Y] batches\nY: ")
        val yBatches = StdIn.readInt()
        print("StepSize: ")
        val yStepSize = StdIn.readInt()

//        println("\nSTART RUNNING WITH EVEN DISTRIBUTED BYTE ARRAYS")
//        new ExperimentBatches(xBytes, xStepSize, yBatches, yStepSize, false).startExperiment()
//
//        println("\nSTART RUNNING WITH EVEN RANDOM BYTE ARRAYS")
//        new ExperimentBatches(xBytes, xStepSize, yBatches, yStepSize, true).startExperiment()
      }
      case 2 => {
        print("Length: ")
        val length = StdIn.readInt()
        print("StepSize: ")
        val stepSize = StdIn.readInt()

//        println("\nSTART RUNNING WITHOUT REPLICATE")
//        new ExperimentGet(length, stepSize, false).startExperiment()
//
//        println("\nSTART RUNNING WITH REPLICATE")
//        new ExperimentGet(length, stepSize, true).startExperiment()
      }
      case 3 => {
        print("Length: ")
        val length = StdIn.readInt()
        print("StepSize: ")
        val stepSize = StdIn.readInt()

        println("\nSTART RUNNING DELIVER EXPERIMENT")
        new ExperimentDeliver(length, stepSize).startExperiment()
      }
      case _ => {
        log.error("Enter a number between 1-5")
        sys.exit(1)
      }
    }

    sys.exit(0)
  }
}