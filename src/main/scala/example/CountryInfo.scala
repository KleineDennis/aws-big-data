package example

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object CountryInfo extends App {

  val spark = SparkSession
    .builder()
    .appName("Spark SQL basic example")
    .getOrCreate()

  import spark.implicits._

  val left = spark.read.parquet("/Users/denniskleine/Downloads/dishwasher-programs/")
  val right = spark.read.format("csv")
    .option("sep", ";")
    .option("header", "true")
    .load("/Users/denniskleine/Downloads/country/")
    .selectExpr("upper(sha2(HAID, 256)) as vid_cdb"
                      ,"left(AUTHORIZATION_GROUP, 2) as country_cdb"
                      ,"substr(" + input_file_name + ", -14, 8) as validFrom")
    .selectExpr("vid_cdb", "country_cdb", "validFrom", "nvl(left(lead(validFrom) over(partition by vid_cdb order by validFrom), 8)-1, 99991230) as validTo")

  val res = left.join(right, ('vid equalTo 'vid_cdb) and ('programstart_date between('validFrom, 'validTo)), "left")
    .withColumn("country", when('country_cdb isNotNull, 'country_cdb) otherwise 'country)
    .drop("vid_cdb", "country_cdb", "validFrom", "validTo")
    .orderBy('programstart_date)

  res.show(false)
}