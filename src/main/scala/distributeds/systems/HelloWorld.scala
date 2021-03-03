package distributeds.systems

import scorex.crypto.authds.LeafData
import scorex.crypto.authds.merkle.{Leaf, MerkleTree}
import scorex.crypto.hash.Keccak256

object HelloWorld extends App {
  implicit val hf = Keccak256

  println("Hello, World!")
  val d = (0 until 10).map(_ => LeafData @@ scorex.utils.Random.randomBytes(32))
  val leafs = d.map(data => Leaf(data))
  val tree = MerkleTree(d)
  leafs.foreach { l =>
    val proof = tree.proofByElement(l).get
    println(s"${proof.leafData}==${l.data}")
    println(proof.leafData == l.data)
    println(s"rootHash: ${tree.rootHash}")
    println(proof.valid(tree.rootHash))
  }
}