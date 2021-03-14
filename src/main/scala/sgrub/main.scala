package sgrub

import sgrub.playground.{ScryptoInMemoryThings, SmartcontractThings}

object main extends App {
  SmartcontractThings.tryDeployAndCall()

  ScryptoInMemoryThings.tryInMemoryGrub()
}
