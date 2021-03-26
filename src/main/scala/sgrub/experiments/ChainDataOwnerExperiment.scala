package sgrub.experiments
import com.google.common.primitives.Longs
import org.bouncycastle.util.encoders.Hex
import org.web3j.protocol.core.methods.response.TransactionReceipt
import scorex.crypto.authds.avltree.batch.{BatchAVLVerifier, InsertOrUpdate}
import scorex.crypto.authds.{ADKey, ADValue}
import sgrub.chain.ChainDataOwner
import sgrub.chain.ChainTools.{log, logGasUsage}
import sgrub.config
import sgrub.contracts._

import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}

class ChainDataOwnerExperiment(
  sp: StorageProvider,
  callback: BigInt => Unit,
  shouldReplicate: Boolean = false,
  smAddress: String = config.getString("sgrub.smContractAddress"),
) extends ChainDataOwner(sp, shouldReplicate, smAddress) {


  override def gPuts(kvs: Map[Long, Array[Byte]]): Boolean = {
    log.info(s"gPuts: ${kvs.map(kv => (kv._1, new String(kv._2)))}")
    val (receivedDigest, receivedProof) = storageProvider.gPuts(kvs)
    val ops = kvs.map(kv => InsertOrUpdate(ADKey @@ Longs.toByteArray(kv._1), ADValue @@ kv._2))
    val verifier = new BatchAVLVerifier[DigestType, HashFunction](
      _latestDigest,
      receivedProof,
      keyLength = KeyLength,
      valueLengthOpt = None,
      maxNumOperations = Some(ops.size),
      maxDeletes = Some(0)
    )(hf)

    ops.foreach(verifier.performOneOperation)

    verifier.digest match {
      case Some(digest) if digest.sameElements(receivedDigest) => {
        _latestDigest = receivedDigest
        log.info(s"Updating digest, new digest: ${Hex.toHexString(receivedDigest)}")
        if (shouldReplicate) {
          callbackFunction("Update digest and replicate",
            () => storageManager.update(kvs.keys.map(Longs.toByteArray).toList.asJava, kvs.values.toList.asJava, _latestDigest).send()) match {
            case Success(_) => true
            case Failure(exception) => {
              log.error(s"Update digest and replicate failed: $exception")
              false
            }
          }
        } else {
          callbackFunction("Update digest",
            () => storageManager.updateDigestOnly(_latestDigest).send()) match {
            case Success(_) => true
            case Failure(exception) => {
              log.error(s"Update digest failed: $exception")
              false
            }
          }
        }
      }
      case _ => false
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
