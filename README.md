# SGRuB

## Requirements

* Scala 2.12.* (2.12.12+)
* JVM 1.8 compatible JDK
* SBT 1.4.9+
* geth 1.10.1+

## Run

Start the private network and miner, follow the instructions in `geth_private/README.md`.

* Start SBT with a variable pointing to the config file: `sbt -Dconfig.file=application.conf`
* Compile: `compile`
* Run: `run`

