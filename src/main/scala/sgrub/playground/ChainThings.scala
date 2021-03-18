package sgrub.playground

import com.google.common.primitives.Longs
import com.typesafe.scalalogging.Logger
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import org.web3j.tx.{Contract, RawTransactionManager}
import org.web3j.tx.gas.StaticGasProvider
import sgrub.chain.{ChainDataOwner, ChainDataUser}
import sgrub.inmemory.InMemoryStorageProvider
import sgrub.smartcontracts.generated.StorageManager.RequestEventResponse
import sgrub.smartcontracts.generated.{Storage, StorageManager, StorageProvider}
import java.math.BigInteger
import java.security.InvalidParameterException

import sgrub.chain.ChainTools.logGasUsage

import scala.io.{BufferedSource, StdIn}
import scala.util.{Failure, Success, Try}
import java.io._
import java.nio.file.{Files, Paths}

class ChainThings(gethPath: String) {

  private val log = Logger(getClass.getName)
  private val web3 = Web3j.build(new HttpService("http://localhost:8101"))
  private val gasProvider = new StaticGasProvider(new BigInteger("1000000000"), new BigInteger("8000000"))
  private val credentials = WalletUtils.loadCredentials("password", s"$gethPath/node01/keystore/UTC--2021-03-14T10-55-22.116143363Z--f90b82d1f4466e7e83740cad7c29f4576334eeb4")
  private val transactionManager = new RawTransactionManager(web3, credentials, 15)
  private var _sp: Option[StorageProvider] = None
  private var _spAddress: Option[String] = None
  private var _sm: Option[StorageManager] = None
  private var _smAddress: Option[String] = None
  def SP: Option[StorageProvider] = _sp
  def SM: Option[StorageManager] = _sm
  def SPAddress: Option[String] = _spAddress
  def SMAddress: Option[String] = _smAddress

  def deploy(): (Try[StorageManager], Try[StorageProvider]) = {
    log.info("Deploying contracts...")
    val tryContractSM = Try(StorageManager.deploy(web3, transactionManager, gasProvider).send())
    val tryContractSP = Try(StorageProvider.deploy(web3, transactionManager, gasProvider).send())
    if (tryContractSM.isSuccess) {
      val contract = tryContractSM.get
      val receipt = contract.getTransactionReceipt
      log.info(s"SM Transaction receipt: $receipt")
      receipt.ifPresent(r => log.info(s"SM deploy gas used: ${r.getGasUsed}"))
      log.info(s"SM Contract address: ${contract.getContractAddress}")
      if (!contract.isValid) {
        return (Failure(new InvalidParameterException("SM Contract was invalid")), tryContractSP)
      }

      _sm = Some(contract)
      _smAddress = Some(contract.getContractAddress)
    }
    if (tryContractSP.isSuccess) {
      val contract = tryContractSP.get
      val receipt = contract.getTransactionReceipt
      log.info(s"SP Transaction receipt: $receipt")
      receipt.ifPresent(r => log.info(s"SP deploy gas used: ${r.getGasUsed}"))
      log.info(s"SP Contract address: ${contract.getContractAddress}")
      if (!contract.isValid) {
        return (tryContractSM, Failure(new InvalidParameterException("SP Contract was invalid")))
      }

      _sp = Some(contract)
      _spAddress = Some(contract.getContractAddress)
    }

    // Store the ContractSM and ContractSP inside a local file.
    create_new_contract_file(tryContractSM.get.getContractAddress, tryContractSP.get.getContractAddress)

    (tryContractSM, tryContractSP)
  }

  def create_new_contract_file(addressSM: String, addressSP : String, path: String = "contracts", extension: String = "contract"): Unit ={
    val contractId = retrieve_contract_id(path, extension)
    val pathname = s"$path/$contractId./$extension"
    val pw = new PrintWriter(new File(pathname))
    pw.write(s"${addressSM};${addressSP}")
    pw.close

    log.info(s"Created a new contract file: $pathname")
  }

  def retrieve_contract_file(contractId: String, path: String = "./contracts", extension: String = "contract"): (Option[String], Option[String]) ={
    val filepath = s"$path/$contractId.$extension"
    println(filepath)
    if(!Files.exists(Paths.get(filepath))){
      println("Path not found")
      return (null, null)
    }

    var reader: BufferedSource = null
    try {
      reader = scala.io.Source.fromFile(filepath)
      val lines = reader.getLines().mkString.split(";")

      // Check if the contract file was valid.
      if(lines.length < 2){
        log.error(s"Contract file ($filepath) was invalid.")
        return (null, null)
      }

      // Return the addresses in the contract file.
      return(Some(lines(0)), Some(lines(1)))
    } catch  {
      case e: Exception => e.printStackTrace()
    } finally {
      if(reader != null){
        reader.close()
      }
    }

    // Contract addresses not found.
    (null, null)
  }

  def retrieve_contract_id(path: String, extension: String): Int = {
    // Retrieve a non existing contract id.
    var contractId = 1;
    while(Files.exists(Paths.get(s"$path/$contractId.$extension"))){
      contractId += 1
    }

    // Return the contract id.
    contractId
  }

  def connect_to_sp(storageAddress: Option[String] = _spAddress): Try[StorageProvider] = {
    SP match {
      case Some(storageExists) => Success(storageExists)
      case _ => storageAddress match {
        case Some(addressExists) => Try(StorageProvider.load(addressExists, web3, transactionManager, gasProvider))
        case _ => Failure(new InvalidParameterException("Storage Provider has not been deployed yet"))
      }
    }
  }

  def connect_to_sm(storageAddress: Option[String] = _spAddress): Try[StorageManager] = {
    SM match {
      case Some(storageExists) => Success(storageExists)
      case _ => storageAddress match {
        case Some(addressExists) => Try(StorageManager.load(addressExists, web3, transactionManager, gasProvider))
        case _ => Failure(new InvalidParameterException("Storage Provider has not been deployed yet"))
      }
    }
  }

  def tryChain(tSM: Try[StorageManager], tSP: Try[StorageProvider]): Unit = {
    tSM match {
      case Success(sm) => {
        tSP match {
          case Success(sp) => {
            val ISP = new InMemoryStorageProvider
            println("Make DO replicate? (y/n)")
            val replicate = StdIn.readBoolean()
            val DO = new ChainDataOwner(ISP, sm, replicate)
            val DU = new ChainDataUser(sp, sm)
            val someNewData = Map[Long, Array[Byte]](
              1L -> "Some Arbitrary Data".getBytes(),
              2L -> "Some More Arbitrary Data".getBytes(),
              3L -> "Hi".getBytes(),
              4L -> "Hello".getBytes(),
            )
            DO.gPuts(someNewData)
            DU.gGet(1L, (key, value) => {
              log.info(s"Value returned. Key: $key, Value: ${new String(value)}")
            })
            // Listen for event requests
            sm.requestEventFlowable(
              DefaultBlockParameterName.EARLIEST,
              DefaultBlockParameterName.LATEST)
              .take(1) // Only taking 1 so it can end...
              .subscribe((event: RequestEventResponse) => {
                log.info(s"Got a request event: key: ${Longs.fromByteArray(event.key)}, sender: ${event.sender}")
                ISP.request(Longs.fromByteArray(event.key), proof => {
                  logGasUsage("SP Emit Deliver", () => sp.emitDeliver(event.key, proof).send())
                })
              })
          }
          case _ => log.error("Deploying SP failed")
        }
      }
      case _ => log.error("Deploying SM failed")
    }

  }

  def userInputThings(): Unit = {
    println(
      "\n" +
        "\n================================" +
        "\nSMART CONTRACT TEST (Full on-chain test)" +
        "\n================================")
    println("Deploy new contract? (y/n)")
    val deployInput = StdIn.readBoolean()
    if (deployInput) {
      val (trySM, trySP) = deploy()
      tryChain(trySM, trySP)
    } else {
      println("Contract file? (leave empty for custom input)")
      val contractFileInput = StdIn.readLine()
      val contractFile = retrieve_contract_file(contractFileInput)

      var spAddress: String = null
      var smAddress: String = null

      // Ask for the SM address if null.
      contractFile match {
        case (Some(a1), Some(a2)) => {
          spAddress = a1.toString
          smAddress = a2.toString
          log.info(s"Using SPAddress ($spAddress) and SMAddress ($smAddress)")
        }
        case (None, None) => {
          spAddress = StdIn.readLine("SP Address?\n")
          smAddress = StdIn.readLine("SM Address?\n")
        }
      }

      // Try the chain.
      tryChain(connect_to_sm(Some(smAddress)), connect_to_sp(Some(spAddress)))
    }
  }
}
