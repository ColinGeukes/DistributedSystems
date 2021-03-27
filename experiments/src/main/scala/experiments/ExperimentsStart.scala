package experiments

object ExperimentsStart {

  def main(args: Array[String]): Unit = {
    var keepRunning = true
    SampleExperiment.run(() => {
      println("Experiment finished, attempting to exit...")
      keepRunning = false
    })
    while (keepRunning) {}
    sys.exit(0)
  }
}