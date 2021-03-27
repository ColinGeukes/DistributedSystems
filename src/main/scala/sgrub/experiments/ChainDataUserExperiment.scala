package sgrub.experiments

import com.google.common.primitives.Longs
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.TransactionReceipt
import scorex.crypto.authds.SerializedAdProof
import sgrub.chain.ChainDataUser
import sgrub.config
import sgrub.smartcontracts.generated.{StorageManager, StorageProviderEventManager}

import scala.concurrent.duration.SECONDS
import scala.util.{Failure, Success, Try}

class ChainDataUserExperiment(
  smAddress: String,
  spAddress: String,
  callback: BigInt => Unit
) extends ChainDataUser {

  override def gGet(key: Long, callback: (Long, Array[Byte]) => Unit): Unit = {
    log.info("Starting SM and SP deliver subscriptions")
    var smSubscription: Option[Disposable] = None
    var spSubscription: Option[Disposable] = None
    smSubscription = Some(storageManager.deliverEventFlowable(
      DefaultBlockParameterName.LATEST,
      DefaultBlockParameterName.LATEST)
      .timeout(config.getInt("sgrub.du.gGetTimeout"), SECONDS)
      .filter((event: StorageManager.DeliverEventResponse) =>
        Longs.fromByteArray(event.key) == key)
      .takeUntil(new Predicate[StorageManager.DeliverEventResponse] {
        override def test(t: StorageManager.DeliverEventResponse): Boolean = Longs.fromByteArray(t.key) == key
      })
      .subscribe((event: StorageManager.DeliverEventResponse) => {
        callback(key, event.value)
        spSubscription match {
          case Some(sub) => sub.dispose()
          case _ =>
        }
      }))
    spSubscription = Some(eventManager.deliverEventFlowable(
      DefaultBlockParameterName.LATEST,
      DefaultBlockParameterName.LATEST)
      .filter((event: StorageProviderEventManager.DeliverEventResponse) =>
        Longs.fromByteArray(event.key) == key)
      .takeUntil(new Predicate[StorageProviderEventManager.DeliverEventResponse] {
        override def test(t: StorageProviderEventManager.DeliverEventResponse): Boolean = verify(key, t.proof.asInstanceOf[SerializedAdProof], callback)
      })
      .timeout(config.getInt("sgrub.du.gGetTimeout"), SECONDS)
      .subscribe((_: StorageProviderEventManager.DeliverEventResponse) => {smSubscription match {
        case Some(sub) => sub.dispose()
        case _ =>
      }
      }))
    log.info(s"Attempting to gGet Key: $key")
    callbackFunction("gGet", () => storageManager.gGet(Longs.toByteArray(key)).send()) match {
      case Success(_) => // Do nothing
      case Failure(exception) => {
        log.error(s"gGet failed, stopping subscriptions. Exception: $exception")
        smSubscription match {
          case Some(sub) => sub.dispose()
          case _ =>
        }
        spSubscription match {
          case Some(sub) => sub.dispose()
          case _ =>
        }
      }
    }
  }

  def callbackFunction(functionName: String, function: () => TransactionReceipt): Try[TransactionReceipt] = {
    val result = Try(function())
    result match {
      case Success(receipt) => {
        val gasUsed = receipt.getGasUsed
        log.info(s"'$functionName' succeeded, gas used: $gasUsed")
        callback(gasUsed)
        result
      }
      case Failure(exception) => {
        log.error(s"'$functionName' failed, unable to measure gas. Exception: $exception")
        result
      }
    }
  }
}
