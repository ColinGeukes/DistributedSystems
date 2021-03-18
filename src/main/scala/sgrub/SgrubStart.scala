package sgrub

import com.typesafe.scalalogging.Logger
import sgrub.playground.{ChainThings, ScryptoInMemoryThings, SmartcontractThings}

object SgrubStart {
  private val log = Logger(getClass.getName)

  def main(args: Array[String]): Unit = {
    if (!config.isResolved) {
      log.error("Unable to resolve config")
      sys.exit(1)
    }
    log.info("Starting...")
//    val smartcontractThings = new SmartcontractThings(args(0))
//    smartcontractThings.userInputThings()
//    smartcontractThings.startListener()
//    //Contract address: 0xf83ae7a717596b94e197fe6c413623a109afb558
//    smartcontractThings.userInputThings()

//    ScryptoInMemoryThings.tryInMemoryGrub()
    println(s"sgrub.gethPath = ${config.getString("sgrub.gethPath")}")
    val chainthings = new ChainThings(config.getString("sgrub.gethPath"))
    chainthings.userInputThings()
  }
}
