package sgrub.chain

import com.typesafe.scalalogging.Logger
import org.web3j.protocol.core.methods.response.TransactionReceipt

import scala.util.{Failure, Success, Try}

object ChainTools {
  private val log = Logger(getClass.getName)

  def logGasUsage(functionName: String, function: () => TransactionReceipt): Try[TransactionReceipt] = {
    val result = Try(function())
    result match {
      case Success(receipt) => {
        log.info(s"'$functionName' succeeded, gas used: ${receipt.getGasUsed}")
        result
      }
      case Failure(exception) => {
        log.error(s"'$functionName' failed, unable to measure gas. Exception: $exception")
        result
      }
    }
  }
}
