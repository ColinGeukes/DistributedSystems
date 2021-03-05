package sgrub.contracts.DataTypes

import sgrub.contracts.DataTypes.ReplicationState.ReplicationState

case class StateKey(
  State: ReplicationState,
  Key: Int
)
