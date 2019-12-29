package com.oreilly.sparkaws

import org.apache.spark.sql.SparkSession

object SparkS3read extends App {

  val spark = SparkSession
    .builder
    .appName("ALSExample")
    .getOrCreate()

  val sc = spark.sparkContext
  val textFile = sc.textFile("s3a://kleinedennis/sonnets.txt") //
  val counts = textFile.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey(_ + _)
  counts.saveAsTextFile("s3a://kleinedennis/result.txt")

  spark.stop()
}
