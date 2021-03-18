package sgrub

import com.typesafe.scalalogging.Logger
import sgrub.playground.{ChainThings, ScryptoInMemoryThings, SmartcontractThings}

object SgrubStart {
  private val log = Logger(getClass.getName)

  def main(args: Array[String]): Unit = {
    if (args.length < 1) {
      log.error("No arguments provided. Please provide the path to geth_private")
      sys.exit(1)
    }
    log.info("Starting...")
//    val smartcontractThings = new SmartcontractThings(args(0))
//    smartcontractThings.userInputThings()
//    smartcontractThings.startListener()
//    //Contract address: 0xf83ae7a717596b94e197fe6c413623a109afb558
//    smartcontractThings.userInputThings()

//    ScryptoInMemoryThings.tryInMemoryGrub()
    val chainthings = new ChainThings(args(0))
    chainthings.userInputThings()
  }
}
