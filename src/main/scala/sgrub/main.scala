package sgrub

import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.filters
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.DefaultGasProvider
import sgrub.inmemory.{InMemoryDataOwner, InMemoryDataUser, InMemoryStorageProvider}
import sgrub.smartcontracts.generated.Greeter

object main extends App {
  val StorageProvider = new InMemoryStorageProvider
  val DU1 = new InMemoryDataUser(StorageProvider)
  val DO1 = new InMemoryDataOwner(StorageProvider)
  DO1.register(DU1)

  val someNewData = Map[Long, Array[Byte]](
    1L -> "Some Arbitrary Data".getBytes(),
    2L -> "Some More Arbitrary Data".getBytes(),
    3L -> "Hi".getBytes(),
    4L -> "Hello".getBytes(),
  )

  val someNewerData = Map[Long, Array[Byte]](
    4L -> "Updated Hello ".getBytes(),
    5L -> "Added Hello".getBytes(),
  )

  val kvPrint: (Long, Array[Byte]) => Unit = (key, value) => {
    println(s"Key: $key, Value: ${new String(value)}")
  }

  println("Initial input:")
  someNewData.foreach(x => kvPrint(x._1, x._2))

  println(s"DO1 verification result: ${DO1.gPuts(someNewData)}")
  println("DU1 gGet results:")
  someNewData.foreach(kv => {
    DU1.gGet(kv._1, kvPrint)
  })
  println("Adding new data...")
  someNewerData.foreach(x => kvPrint(x._1, x._2))
  println(s"DO1 verification result: ${DO1.gPuts(someNewerData)}")
  println("DU1 gGet results:")
  someNewerData.foreach(kv => {
    DU1.gGet(kv._1, kvPrint)
  })
}
