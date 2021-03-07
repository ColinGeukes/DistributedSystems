package sgrub

import scorex.crypto.hash.{Digest32, Blake2b256}

package object contracts {
  def KeyLength: Int = 8
  type DigestType = Digest32
  type HashFunction = Blake2b256.type
}
