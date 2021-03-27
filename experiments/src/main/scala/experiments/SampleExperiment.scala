package experiments

import org.web3j.crypto.WalletUtils
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.RawTransactionManager
import sgrub.chain.{ChainDataOwner, ChainDataUser, StorageProviderChainListener, gasProvider, web3}
import sgrub.config
import sgrub.inmemory.InMemoryStorageProvider
import sgrub.smartcontracts.generated.{StorageManager, StorageProviderEventManager}

import scala.util.{Failure, Success, Try}

object SampleExperiment {
  def customGasLog(functionName: String, function: () => TransactionReceipt): Try[TransactionReceipt] = {
    val result = Try(function())
    result match {
      case Success(receipt) => {
        println(s"'$functionName' succeeded, gas used: ${receipt.getGasUsed}")
        result
      }
      case Failure(exception) => {
        println(s"'$functionName' failed, unable to measure gas. Exception: $exception")
        result
      }
    }
  }

  def veryCustomGasLog(extraParam: String, functionName: String, function: () => TransactionReceipt): Try[TransactionReceipt] = {
    val result = Try(function())
    println(s"I have an extra parameter! $extraParam")
    result match {
      case Success(receipt) => {
        println(s"'$functionName' succeeded, gas used: ${receipt.getGasUsed}")
        result
      }
      case Failure(exception) => {
        println(s"'$functionName' failed, unable to measure gas. Exception: $exception")
        result
      }
    }
  }

  def run(): Unit = {
    // Deploy clean contracts
    val doCredentials = WalletUtils.loadCredentials(config.getString("sgrub.do.password"), config.getString("sgrub.do.keyLocation"))
    val doTransactionManager = new RawTransactionManager(web3, doCredentials, config.getInt("sgrub.chainId"))
    val spCredentials = WalletUtils.loadCredentials(config.getString("sgrub.sp.password"), config.getString("sgrub.sp.keyLocation"))
    val spTransactionManager = new RawTransactionManager(web3, spCredentials, config.getInt("sgrub.chainId"))
    val sm = StorageManager.deploy(web3, doTransactionManager, gasProvider).send()
    val sp = StorageProviderEventManager.deploy(web3, spTransactionManager, gasProvider).send()

    // Create DO, SP and DU
    val SP = new InMemoryStorageProvider
    val DO = new ChainDataOwner(
      sp = SP,
      shouldReplicate = false,
      logGasUsage = customGasLog,
      smAddress = sm.getContractAddress)
    val listener = new StorageProviderChainListener(
      storageProvider = SP,
      logGasUsage = (functionName, function) => veryCustomGasLog("Something else", functionName, function),
      smAddress = sm.getContractAddress,
      spAddress = sp.getContractAddress
    ).listen()
    val DU = new ChainDataUser(
      logGasUsage = customGasLog,
      smAddress = sm.getContractAddress,
      spAddress = sp.getContractAddress
    )

    // Add some things
    DO.gPuts(Map(
      1L -> "Some Arbitrary Data".getBytes(),
      2L -> "Some More Arbitrary Data".getBytes(),
      3L -> "Hi".getBytes(),
      4L -> "Hello".getBytes(),
    ))

    // Try to get one
    DU.gGet(1L, (key, value) => {
      println(s"Successfully got key: $key, value: ${new String(value)}")
      listener.dispose()
      sys.exit(0)
    })
  }
}
