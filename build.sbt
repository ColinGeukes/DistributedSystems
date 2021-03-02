import sbt.Keys.{scalaVersion}

name := "GRuB_Scala"
description := "GRuB for Scala"

version := "0.1"

scalaVersion := "2.12.12"

resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"

libraryDependencies += "org.scorexfoundation" %% "scrypto" % "2.1.10"