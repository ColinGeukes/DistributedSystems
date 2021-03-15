package sgrub.playground

import io.reactivex.subscribers.DisposableSubscriber
import org.web3j.abi.{EventEncoder, TypeReference}
import org.web3j.abi.datatypes.{Address, Event, Uint}
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.http.HttpService
import org.web3j.tx.RawTransactionManager
import org.web3j.tx.gas.StaticGasProvider
import sgrub.smartcontracts.generated.Storage

import java.math.BigInteger
import java.security.InvalidParameterException
import java.util
import scala.io.StdIn
import scala.util.{Failure, Success, Try}

class SmartcontractThings(gethPath: String) {
  private val web3 = Web3j.build(new HttpService("http://localhost:8101"))
  private val gasProvider = new StaticGasProvider(new BigInteger("1000000000"), new BigInteger("8000000"))
  private val credentials = WalletUtils.loadCredentials("password", s"$gethPath/node01/keystore/UTC--2021-03-14T10-55-22.116143363Z--f90b82d1f4466e7e83740cad7c29f4576334eeb4")
  private val transactionManager = new RawTransactionManager(web3, credentials, 15)
  private var _storage: Option[Storage] = None
  def storage: Option[Storage] = _storage
  private var _address: Option[String] = None
  def address: Option[String] = _address

  def deploy_storage(): Try[Storage] = {
    println("Deploying contract...")
    val tryContract = Try(Storage.deploy(
      web3, transactionManager, gasProvider).send())
    if (tryContract.isSuccess) {
      val contract = tryContract.get
      println(s"Transaction receipt: ${contract.getTransactionReceipt}")
      println(s"Contract address: ${contract.getContractAddress}")
      if (!contract.isValid) {
        return Failure(new InvalidParameterException("Contract was invalid"))
      }
      _storage = Some(contract)
      _address = Some(contract.getContractAddress)
    }
    tryContract
  }

  def connect_to_storage(storageAddress: Option[String] = address): Try[Storage] = {
    storage match {
      case Some(storageExists) => Success(storageExists)
      case _ => storageAddress match {
        case Some(addressExists) => Try(Storage.load(addressExists, web3, transactionManager, gasProvider))
        case _ => Failure(new InvalidParameterException("Storage has not been deployed yet"))
      }
    }
  }

  def tryCall(tryStorage: Try[Storage]): Unit = {
    tryStorage match {
      case Success(storage) => {
        println("Number to store?")
        val toStore = BigInteger.valueOf(StdIn.readInt())
        println(s"Storing $toStore")
        storage.store(toStore).send()
        println("Stored. Retrieving...")
        println(s"Retrieved: ${storage.retrieve().send()}")
      }
      case Failure(ex) => println(s"Failed with: $ex")
    }
  }

  def userInputThings(): Unit = {
    println(
      "\n" +
        "\n================================" +
        "\nSMART CONTRACT TEST (Basic Storage)" +
        "\n================================")
    println("Deploy new contract? (y/n)")
    val deployInput = StdIn.readBoolean()
    if (deployInput) {
      tryCall(deploy_storage())
    } else {
      val inputAddress = StdIn.readLine("Please enter the storage contract address: ")
      tryCall(connect_to_storage(Some(inputAddress)))
    }
  }

  def startListener():Unit = {
    new EventListener().listen()
  }

  private class EventListener(){
    // Definition of event: request(uint indexed key, address indexed sender);
    val event = new Event(
      "request",
      util.Arrays.asList[TypeReference[_]](
        new TypeReference[Uint](true) {},
        new TypeReference[Address](true) {}));

    //need get the encoding for the event of interest to filter from EVM log
    val eventHash = EventEncoder.encode(event);

    //filter on address, blocks, and event definition
    val filter = new EthFilter(
      DefaultBlockParameterName.EARLIEST, //search from block (maybe change to latest?)
      DefaultBlockParameterName.LATEST, // to block
      _address.toString // smart contract that emits event
    ).addSingleTopic(eventHash); //filter on event definition

    // subscriber logic for handling incoming event logs
    val subscriber = new DisposableSubscriber[Any]() {
      override def onNext(t: Any): Unit = println(t.toString)
      override def onError(t: Throwable): Unit = t.printStackTrace()
      override def onComplete(): Unit = println("stopped listening")
    }

    def listen(): Unit ={
      println("listening")
      web3.ethLogFlowable(filter).subscribeWith(subscriber);
    }

    def stopListening(): Unit={
      subscriber.dispose()
    }
  }
}
