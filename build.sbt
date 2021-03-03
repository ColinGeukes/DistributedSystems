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