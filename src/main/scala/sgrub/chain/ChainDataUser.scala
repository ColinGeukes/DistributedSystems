package sgrub.chain

import com.google.common.primitives.Longs
import com.typesafe.scalalogging.Logger
import io.reactivex.disposables.Disposable
import org.web3j.protocol.core.DefaultBlockParameterName
import scorex.crypto.authds.avltree.batch.{BatchAVLVerifier, Lookup}
import scorex.crypto.authds.{ADDigest, ADKey, SerializedAdProof}
import sgrub.contracts._
import sgrub.smartcontracts.generated.{StorageManager, StorageProvider}

import scala.util.{Failure, Success}

class InMemoryDataUser(
  sp: StorageProvider,
  sm: StorageManager
) extends DataUser {
  private val log = Logger(getClass.getName)

  override def gGet(key: Long, callback: (Long, Array[Byte]) => Unit): Unit = {
    val transactionAwait = sm.gGet(Longs.toByteArray(key)).sendAsync()
    var smSubscription: Option[Disposable] = None
    var spSubscription: Option[Disposable] = None
    smSubscription = Some(sm.deliverEventFlowable(
      DefaultBlockParameterName.EARLIEST,
      DefaultBlockParameterName.LATEST)
      .filter((event: StorageManager.DeliverEventResponse) =>
        Longs.fromByteArray(event.key) == key)
      .takeUntil((event: StorageManager.DeliverEventResponse) =>
        Longs.fromByteArray(event.key) == key)
      .subscribe((event: StorageManager.DeliverEventResponse) => {
        callback(key, event.value)
        spSubscription match {
          case Some(sub) => sub.dispose()
        }
      }))
    spSubscription = Some(sp.deliverEventFlowable(
      DefaultBlockParameterName.EARLIEST,
      DefaultBlockParameterName.LATEST)
      .filter((event: StorageProvider.DeliverEventResponse) =>
        Longs.fromByteArray(event.key) == key)
      .takeUntil((event: StorageProvider.DeliverEventResponse) =>
        verify(key, SerializedAdProof @@ event.proof, callback))
      .subscribe())
  }

  private def verify(key: Long, proof: SerializedAdProof, callback: (Long, Array[Byte]) => Unit): Boolean = {
    val verifier = new BatchAVLVerifier[DigestType, HashFunction](
      ADDigest @@ sm.digest().send(),
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
