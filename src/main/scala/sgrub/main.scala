package sgrub

import java.nio.charset.StandardCharsets

import sgrub.contracts.{DataOwner, MaliciousStorageProvider, StorageProvider}

object main extends App {
  val StorageProvider = new StorageProvider
  val MalSP = new MaliciousStorageProvider
  val DO1 = new DataOwner(StorageProvider)
  val DO2 = new DataOwner(MalSP)

  val someNewData = Map[Long, Array[Byte]](
    1L -> "Some Arbitrary Data".getBytes(StandardCharsets.UTF_8),
    2L -> "Some More Arbitrary Data".getBytes(StandardCharsets.UTF_8),
    3L -> "Hi".getBytes(StandardCharsets.UTF_8),
    4L -> "Hello".getBytes(StandardCharsets.UTF_8),
  )

  val kvPrinter: (Long, Array[Byte]) => Unit = (key, value) => {
    println(s"Key: $key, Value: ${new String(value, StandardCharsets.UTF_8)}")
  }

  println("Initial input:")
  someNewData.foreach(x => kvPrinter(x._1, x._2))



  println(s"DO1 verification result: ${DO1.gPuts(someNewData)}")
  println("DO1 storage provider contents:")
  someNewData.foreach(kv => {
    StorageProvider.gGet(kv._1, kvPrinter)
  })
  println(s"DO2 verification result: ${DO2.gPuts(someNewData)}")
  println("DO2 storage provider contents:")
  someNewData.foreach(kv => {
    MalSP.gGet(kv._1, kvPrinter)
  })
}
