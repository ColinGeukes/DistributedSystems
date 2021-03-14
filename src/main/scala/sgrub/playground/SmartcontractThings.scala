package sgrub.playground

import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.RawTransactionManager
import org.web3j.tx.gas.StaticGasProvider
import sgrub.smartcontracts.generated.Greeter

import java.math.BigInteger
import java.security.InvalidParameterException
import scala.io.StdIn
import scala.util.{Failure, Success, Try}

class SmartcontractThings(gethPath: String) {
  private val web3 = Web3j.build(new HttpService("http://localhost:8101"))
  private val gasProvider = new StaticGasProvider(new BigInteger("10"), new BigInteger("8000000"))
  private val credentials = WalletUtils.loadCredentials("password", s"$gethPath/node01/keystore/UTC--2021-03-14T10-55-22.116143363Z--f90b82d1f4466e7e83740cad7c29f4576334eeb4")
  private val transactionManager = new RawTransactionManager(web3, credentials, 15)
  private var _greeter: Option[Greeter] = None
  def greeter: Option[Greeter] = _greeter
  private var _address: Option[String] = None
  def address: Option[String] = _address

  def deploy_greeter(greeting: String = "Hi"): Try[Greeter] = {
    println("Deploying contract...")
    val tryContract = Try(Greeter.deploy(
      web3, transactionManager, gasProvider, greeting).send())
    if (tryContract.isSuccess) {
      val contract = tryContract.get
      println(s"Transaction receipt: ${contract.getTransactionReceipt}")
      var retryCount = 10
      while (retryCount > 0) {
        if (!contract.getContractAddress.isEmpty) {
          println(s"Contract address: ${contract.getContractAddress}")
          retryCount = 0
        } else {
          println("No contract address yet, waiting 10 seconds...")
          Thread.sleep(10000)
          retryCount -= 1
        }
      }
      if (!contract.isValid) {
        return Failure(new InvalidParameterException("Contract was invalid"))
      }
      _greeter = Some(contract)
      _address = Some(contract.getContractAddress)
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

  def tryCall(tryGreeter: Try[Greeter]): Unit = {
    tryGreeter match {
      case Success(greeter) => {
        println(s"Response: ${greeter.greet().send()}")
      }
      case Failure(ex) => println(s"Failed with: $ex")
    }
  }

  def userInputThings(): Unit = {
    println("Deploy new contract? (y/n)")
    val deployInput = StdIn.readBoolean()
    if (deployInput) {
      tryCall(deploy_greeter("Hello there"))
    } else {
      val inputAddress = StdIn.readLine("Please enter the greeter contract address: ")
      tryCall(connect_to_greeter(Some(inputAddress)))
    }
  }
}
