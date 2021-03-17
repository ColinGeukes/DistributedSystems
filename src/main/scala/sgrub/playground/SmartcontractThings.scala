package sgrub.playground

import io.reactivex.subscribers.DisposableSubscriber
import org.web3j.abi.{EventEncoder, TypeReference}
import org.web3j.abi.datatypes.{Address, Event, Uint}
import org.web3j.crypto.{RawTransaction, WalletUtils}
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.{EthFilter, Transaction}
import org.web3j.protocol.http.HttpService
import org.web3j.tx.{Contract, RawTransactionManager}
import org.web3j.tx.gas.StaticGasProvider
import scorex.crypto.authds.SerializedAdProof
import sgrub.smartcontracts.generated.{Storage, StorageProvider}
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.EthGetTransactionCount

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
  private var _storage: Option[Contract] = None

  def storage: Option[Contract] = _storage

  private var _address: Option[String] = None

  def address: Option[String] = _address

  def deploy(s: String): Try[Any] = {
    println("Deploying contract...")
    val tryContract = s match {
      case "Storage" => {
        println("Deploying Storage contract...")
        Try(Storage.deploy(web3, transactionManager, gasProvider).send())
      }
      case "StorageProvider" => {
        println("Deploying StorageProvider contract...")
        Try(StorageProvider.deploy(web3, transactionManager, gasProvider).send())
      }
    }
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

  def connect_to_storage(storageAddress: Option[String] = address, s: String = "Storage"): Try[Contract] = {
    storage match {
      case Some(storageExists) => Success(storageExists)
      case _ => storageAddress match {
        case Some(addressExists) => {
          s match {
            case "Storage" => Try(Storage.load(addressExists, web3, transactionManager, gasProvider))
            case "StorageProvider" => Try(StorageProvider.load(addressExists, web3, transactionManager, gasProvider))
          }
        }
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

  def tryCall2(tryStorage: Try[StorageProvider]): Unit = {
    tryStorage match {
      case Success(storage) => {
        println("key to store?")
        val toStore = BigInteger.valueOf(StdIn.readInt())
        val bytes = "test".getBytes()
        println(s"Storing test bytes at $toStore")
        storage.update(toStore, bytes, bytes).send()
        println("Stored")
      }
      case Failure(ex) => println(s"Failed with: $ex")
    }
  }

  def tryCall3(tryStorage: Try[StorageProvider]): Unit = {
    tryStorage match {
      case Success(storage) => {
        println("key to get?")
        val toGet = BigInteger.valueOf(StdIn.readInt())
        println(s"Getting bytes at $toGet")
        storage.gGet(toGet).send()
        println("Request sent")
        val receipt = _storage.get.getTransactionReceipt.get()
        println("Printing transaction event log: ")
        storage.getRequestEvents(receipt).forEach(e => {
          val key = e.key
          val sender = e.sender
          println(s"found request for $key from $sender")
        })
      }
      case Failure(ex) => println(s"Failed with: $ex")
    }
  }

  /*
    when an event happens with a KV request from some client
    send transaction with the data+proof to the requesting address.
    The from address needs to be included to compute the correct nonce
  */
  def respondToEvent(key: Int, requester: String, from: String = "0xf90b82d1f4466e7e83740cad7c29f4576334eeb4") = {
    //get nonce for new transaction
    val count = web3.ethGetTransactionCount(from, DefaultBlockParameterName.LATEST).sendAsync.get
    val nonce = count.getTransactionCount

    //transaction with 0 ETH and some custom data
    val transaction = RawTransaction.createTransaction(
      nonce.add(new BigInteger("1")),
      gasProvider.getGasPrice,
      gasProvider.getGasLimit(),
      requester, //to
      new BigInteger("0"), //value of transaction
      "some data for key:  " + key.toString //data of transaction => should be proof bytes
    );
    println("Trying to send....")
    val result = transactionManager.signAndSend(transaction)
    println("SENT")
    println("raw: " + result.getRawResponse)
    println("result: " + result.getResult)
    if (result.getError != null) println("error: " + result.getError.getMessage)
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
      StdIn.readLine("Name of generated class? (Storage/StorageProvider)\n") match {
        case "Storage" => tryCall(deploy("Storage").asInstanceOf[Try[Storage]])
        case "StorageProvider" => tryCall2(deploy("StorageProvider").asInstanceOf[Try[StorageProvider]])
      }
    } else {
      val generatedClass = StdIn.readLine("Name of generated class? (Storage/StorageProvider)\n")
      val inputAddress = StdIn.readLine("Please enter the storage contract address:\n")
      tryCall3(connect_to_storage(Some(inputAddress), s = generatedClass).asInstanceOf[Try[StorageProvider]])
    }
  }

  def startListener(): Unit = {
    new EventListener().listen()
  }

  private class EventListener() {
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
      _address.get // smart contract that emits event
    ).addSingleTopic(eventHash); //filter on event definition

    // subscriber logic for handling incoming event logs
    val subscriber = new DisposableSubscriber[Any]() {
      override def onNext(t: Any): Unit = {
        println("SUCCESS:  " + t.toString)
        //parse key and address from string formatted as below:
        //Log{removed=false, logIndex='0x0', transactionIndex='0x0', transactionHash='0x479e01d34c7b733ff2d882fcd7a47a0c751ee1d38b28758c0f4057279cedc9c2', blockHash='0x5424c7d60878b19f9674ebc74018ebef459e1820bf8a76c31fa5d3f584b351a8', blockNumber='0x936', address='0xd99480d7863ff1d4e13ddf1356e395039cd230d7', data='0x', type='null', topics=[0x9ad8a98020b6dad44acbf88ecd6cf7536823e301cab64c14479f81dc2980898e, 0x0000000000000000000000000000000000000000000000000000000000000002, 0x000000000000000000000000f90b82d1f4466e7e83740cad7c29f4576334eeb4]}
        val topics = t.toString.stripSuffix("]}").split(", 0x")
        val l = topics.length
        val address = strip0(topics(l-1))
        val key = strip0(topics(l-2)).toInt
        respondToEvent(key, address)
      }

      override def onError(t: Throwable): Unit = t.printStackTrace()

      override def onComplete(): Unit = println("stopped listening")
    }

    //strip ALL leading 0s
    def strip0(s: String): String = {
      if(s.substring(0,1).equals("0")) strip0(s.substring(1))
      else s
    }

    def listen(): Unit = {
      println("listening")
      web3.ethLogFlowable(filter).subscribeWith(subscriber);
    }

    def stopListening(): Unit = {
      subscriber.dispose()
    }
  }

}
