package sgrub

import java.nio.charset.StandardCharsets

import sgrub.inmemory.{InMemoryDataOwner, InMemoryStorageProvider, MaliciousStorageProvider}

object main extends App {
  val StorageProvider = new InMemoryStorageProvider
  val MalSP = new MaliciousStorageProvider
  val DO1 = new InMemoryDataOwner(StorageProvider)
  val DO2 = new InMemoryDataOwner(MalSP)

  val someNewData = Map[Long, Array[Byte]](
    1L -> "Some Arbitrary Data".getBytes(),
    2L -> "Some More Arbitrary Data".getBytes(),
    3L -> "Hi".getBytes(),
    4L -> "Hello".getBytes(),
  )

  val kvPrinter: (Long, Array[Byte]) => Unit = (key, value) => {
    println(s"Key: $key, Value: ${new String(value)}")
  }

  println("Initial input:")
  someNewData.foreach(x => kvPrinter(x._1, x._2))



  println(s"DO1 verification result: ${DO1.gPuts(someNewData)}")
  println("DO1 storage provider contents:")
  someNewData.foreach(kv => {
    StorageProvider.request(kv._1, kvPrinter)
  })
  println(s"DO2 verification result: ${DO2.gPuts(someNewData)}")
  println("DO2 storage provider contents:")
  someNewData.foreach(kv => {
    MalSP.request(kv._1, kvPrinter)
  })
}
