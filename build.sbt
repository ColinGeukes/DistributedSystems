name := "GRuB_Scala"
version := "0.1"
description := "GRuB for Scala"

scalaVersion := "2.12.12"

resolvers ++= Seq(
  "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
)

libraryDependencies ++= Seq(
  "org.scorexfoundation" %% "scrypto" % "2.1.10",
)

val web3jVersion = "4.8.4"

// See https://docs.web3j.io/modules.html
libraryDependencies ++= Seq(
  "org.web3j"              %  "abi"                     % web3jVersion withSources(), // Application Binary Interface encoders
  "org.web3j"              %  "codegen"                 % web3jVersion withSources(), // Code generators
  "org.web3j"              %  "core"                    % web3jVersion withSources(), // ...Core
  "org.web3j"              %  "geth"                    % web3jVersion withSources(), // Geth-specific JSON-RPC module
  "org.web3j"              %  "rlp"                     % web3jVersion withSources(), // Recursive Length Prefix (RLP) encoders
  "org.web3j"              %  "utils"                   % web3jVersion withSources(), // Minimal set of utility classes
  "org.web3j"              %  "web3j-maven-plugin"      % "4.6.5"      withSources(), // Create Java classes from solidity contract files
)