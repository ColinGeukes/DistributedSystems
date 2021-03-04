package distributeds.systems

import distributeds.systems.ADS.MerkleTree

object HelloWorld extends App {
  val key = 1
  val data = new Array[Byte](0.toByte)
  val tree = new MerkleTree[Int, Array[Byte]](3)

  println(tree.maxKey)
  var i = 0
  for(i <- 1 to 10 ){
    tree.insert(i,data)
  }

  println(tree.prettyStringWithValues)
}