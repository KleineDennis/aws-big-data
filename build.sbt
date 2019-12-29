import Dependencies._

ThisBuild / scalaVersion     := "2.11.8" //2.12.10
ThisBuild / version          := "0.1.0"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

mainClass in (Compile, packageBin) := Some("com.oreilly.sparkaws.ALSExample")
mainClass in (Compile, run) := Some("com.oreilly.sparkaws.ALSExample")

//lazy val sparkVersion = "3.0.0-preview"
lazy val sparkVersion = "2.4.4"

lazy val root = (project in file("."))
  .settings(
      name := "aws-big-data",
      libraryDependencies += scalaTest % Test,
      libraryDependencies += sparkTest % Test,
      libraryDependencies += "org.apache.spark"  %% "spark-core"  % sparkVersion,
      libraryDependencies += "org.apache.spark"  %% "spark-sql"   % sparkVersion,
      libraryDependencies += "org.apache.spark"  %% "spark-mllib" % sparkVersion,
      libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "2.10.0",
      libraryDependencies += "org.apache.hadoop" % "hadoop-aws" % "2.10.0",
      libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.11.452",
  )
