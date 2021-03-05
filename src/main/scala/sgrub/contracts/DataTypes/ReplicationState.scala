package sgrub.contracts.DataTypes

object ReplicationState extends Enumeration {
  type ReplicationState = Value

  val NonReplicated, Replicated = Value
}
