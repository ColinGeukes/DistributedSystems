package sgrub.contracts


import com.google.common.primitives.{Bytes, Ints, Longs}
import scorex.crypto.authds.{ADKey, ADValue}
import scorex.crypto.authds.avltree.batch.{BatchAVLProver, InsertOrUpdate}
import scorex.crypto.hash.{CryptographicHash, Digest}
import sgrub.contracts.DataTypes.StateKey

import scala.util.{Failure, Success, Try}

// Given a stream of data updates
// ... DO sends a gPuts call every epoch
// To prepare the call, DO locally batches the data updates
// ... and includes them in a single gPuts call
// ... to be sent by the end of the epoch.
class DataOwner {
  val prover = new BatchAVLProver(4, valueLengthOpt = Some(8))
  val initialDigest = prover.digest


  def gPuts(kvs: Map[Int, Long]): Boolean = {
    // Internally, the gPuts...
    // First
    // > notifies the control plane on DO of the latest data updates
    // Then,
    // | for each data update:
    // > DO and SP jointly run the ADS protocol to securely update matching KV records


    val ops = kvs.map(kv => InsertOrUpdate(ADKey @@ Ints.toByteArray(kv._1), ADValue @@ Longs.toByteArray(kv._2)))
    if (ops.map(prover.performOneOperation).exists(result => result.isFailure))
      return false

    val proof = prover.generateProof()
    val digest = prover.digest

    // | If:
    // | All KV records in this batch are in non-replicated state (NR)
    // | and there is no update on the replication state
    // > The DO sends only the digest of this batch to call the update() function in the storage-manager smart contract
    /// (Note that the blockchain node on the DO receiving the update() call would propagate it
    /// to other blockchain nodes.)
    // | If:
    // | There are any KV records with replicated state (R)
    // > They are included in the update() call.
    // | If:
    // | There is any state transition, either from R to NR or from NR to R
    // > Such transitions are included in the update call

    true
  }
}
