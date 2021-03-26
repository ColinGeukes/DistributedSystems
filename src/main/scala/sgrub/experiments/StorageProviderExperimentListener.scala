package sgrub.experiments

import com.google.common.primitives.Longs
import com.typesafe.scalalogging.Logger
import io.reactivex.disposables.Disposable
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.tx.RawTransactionManager
import sgrub.chain.ChainTools.logGasUsage
import sgrub.config
import sgrub.contracts.StorageProvider
import sgrub.smartcontracts.generated.StorageManager.RequestEventResponse
import sgrub.smartcontracts.generated.{StorageManager, StorageProviderEventManager}

import scala.util.{Failure, Success, Try}

class StorageProviderExperimentListener(
  storageProvider: StorageProvider,
  smAddress: String = config.getString("sgrub.smContractAddress"),
  spAddress: String = config.getString("sgrub.spContractAddress")
) {
  private val log = Logger(getClass.getName)
  private val credentials = WalletUtils.loadCredentials(config.getString("sgrub.sp.password"), config.getString("sgrub.sp.keyLocation"))
  private val transactionManager = new RawTransactionManager(web3, credentials, config.getInt("sgrub.chainId"))
  private var smSubscription: Option[Disposable] = None
  private val storageManager: StorageManager = Try(StorageManager.load(smAddress, web3, transactionManager, gasProvider)) match {
    case Success(sm) => sm
    case Failure(exception) => {
      log.error(s"Unable to load StorageManager with address $smAddress, exception: $exception")
      sys.exit(1)
    }
  }
  private val eventManager: StorageProviderEventManager = Try(StorageProviderEventManager.load(spAddress, web3, transactionManager, gasProvider)) match {
    case Success(sm) => sm
    case Failure(exception) => {
      log.error(s"Unable to load StorageProviderEventManager with address $spAddress, exception: $exception")
      sys.exit(1)
    }
  }


  def listen(): Disposable = {
    smSubscription match {
      case Some(subscription) => subscription
      case _ => {
        val subscription = storageManager.requestEventFlowable(
          DefaultBlockParameterName.LATEST,
          DefaultBlockParameterName.LATEST)
          .subscribe((event: RequestEventResponse) => {


            log.info("Done with operation!!!")

//            log.info(s"Got a request event: key: ${Longs.fromByteArray(event.key)}, sender: ${event.sender}")
//            storageProvider.request(Longs.fromByteArray(event.key), proof => {
//              logGasUsage("SP Emit Deliver", () => eventManager.emitDeliver(event.key, proof).send())
//            })
          })
        smSubscription = Some(subscription)
        subscription
      }
    }
  }
}
