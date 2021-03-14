package sgrub.playground

import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.RawTransactionManager
import org.web3j.tx.gas.{StaticGasProvider}
import sgrub.smartcontracts.generated.Greeter

import java.math.BigInteger
import java.security.InvalidParameterException
import scala.util.{Failure, Success, Try}

object SmartcontractThings {
  private val web3 = Web3j.build(new HttpService("http://localhost:8101"))
  private val gasProvider = new StaticGasProvider(new BigInteger("1"), new BigInteger("8000000"))
  private val credentials = WalletUtils.loadCredentials("password", getClass.getResource("/geth_private/node01/keystore/UTC--2021-03-14T10-55-22.116143363Z--f90b82d1f4466e7e83740cad7c29f4576334eeb4").getPath)
  private val transactionManager = new RawTransactionManager(web3, credentials, 15)
  private var _greeter: Option[Greeter] = None
  def greeter: Option[Greeter] = _greeter
  private var _address: Option[String] = None
  def address: Option[String] = _address

  def deploy_greeter(greeting: String = "Hi"): Try[Greeter] = {
    val tryContract = Try(Greeter.deploy(
      web3, transactionManager, gasProvider, "Hi").send())
    if (tryContract.isSuccess) {
      _greeter = Some(tryContract.get)
      _address = Some(tryContract.get.getContractAddress)
    }
    tryContract
  }

  def connect_to_greeter(greeterAddress: Option[String] = address): Try[Greeter] = {
    greeter match {
      case Some(greeterExists) => Success(greeterExists)
      case _ => greeterAddress match {
        case Some(addressExists) => Try(Greeter.load(addressExists, web3, transactionManager, gasProvider))
        case _ => Failure(new InvalidParameterException("Greeter has not been deployed yet"))
      }
    }
  }

  def tryDeployAndCall(): Unit = {
    println("Trying to deploy greeter...")
    deploy_greeter("Hello there") match {
      case Success(greeter) => {
        println("Deployed greeter, trying to call...")
        println(s"Response: ${greeter.greet().send()}")
      }
      case Failure(ex) => println(s"Failed with: $ex")
    }
  }
}
