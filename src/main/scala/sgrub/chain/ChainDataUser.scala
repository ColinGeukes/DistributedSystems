package sgrub.chain

import com.google.common.primitives.Longs
import com.typesafe.scalalogging.Logger
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import org.web3j.protocol.core.DefaultBlockParameterName
import scorex.crypto.authds.avltree.batch.{BatchAVLVerifier, Lookup}
import scorex.crypto.authds.{ADDigest, ADKey, SerializedAdProof}
import sgrub.chain.ChainTools.logGasUsage
import sgrub.contracts._
import sgrub.smartcontracts.generated.{StorageManager, StorageProvider}

import scala.util.{Failure, Success}

class ChainDataUser(
  sp: StorageProvider,
  sm: StorageManager
) extends DataUser {
  private val log = Logger(getClass.getName)

  override def gGet(key: Long, callback: (Long, Array[Byte]) => Unit): Unit = {
    log.info("Starting SM and SP deliver subscriptions")
    var smSubscription: Option[Disposable] = None
    var spSubscription: Option[Disposable] = None
    smSubscription = Some(sm.deliverEventFlowable(
      DefaultBlockParameterName.EARLIEST,
      DefaultBlockParameterName.LATEST)
//      .subscribe((event: StorageManager.DeliverEventResponse) => {
//        log.info(s"Got SM Deliver event! key: ${Longs.fromByteArray(event.key)}, value: ${new String(event.value)}")
//      }))
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
    spSubscription = Some(sp.deliverEventFlowable(
      DefaultBlockParameterName.EARLIEST,
      DefaultBlockParameterName.LATEST)
//      .subscribe((event: StorageProvider.DeliverEventResponse) => {
//        log.info(s"Got SP Deliver event! key: ${Longs.fromByteArray(event.key)}, proof: ${event.proof.mkString(", ")}")
//      }))
      .filter((event: StorageProvider.DeliverEventResponse) =>
        Longs.fromByteArray(event.key) == key)
      .takeUntil(new Predicate[StorageProvider.DeliverEventResponse] {
        override def test(t: StorageProvider.DeliverEventResponse): Boolean = verify(key, t.proof.asInstanceOf[SerializedAdProof], callback)
      })
      .subscribe((_: StorageProvider.DeliverEventResponse) => {smSubscription match {
        case Some(sub) => sub.dispose()
        case _ =>
      }
      }))
    log.info(s"Attempting to gGet Key: $key")
    logGasUsage("gGet", () => sm.gGet(Longs.toByteArray(key)).send()) match {
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

  private def verify(key: Long, proof: SerializedAdProof, callback: (Long, Array[Byte]) => Unit): Boolean = {
    log.info(s"Verifying for key: $key")
    log.info("Getting digest...")
    val latestDigest = ADDigest @@ sm.getDigest().send()
    log.info(s"Got digest: $latestDigest")
    val verifier = new BatchAVLVerifier[DigestType, HashFunction](
      latestDigest,
      proof,
      keyLength = KeyLength,
      valueLengthOpt = None,
      maxNumOperations = Some(1),
      maxDeletes = Some(0)
    )(hf)

    verifier.performOneOperation(Lookup(ADKey @@ Longs.toByteArray(key))) match {
      case Success(successResult) => successResult match {
        case Some(existResult) => {
          callback(key, existResult)
          true
        }
        case _ => {
          log.error(s"Fail. No value for key: $key")
          false
        }
      }
      case Failure(exception) => {
        log.error(s"Fail. $exception")
        false
      }
    }
  }
}
