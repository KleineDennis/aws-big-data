//package example
//
//import java.io.{File, IOException}
//import java.nio.file._
//import java.nio.file.attribute.BasicFileAttributes
//​import com.holdenkarau.spark.testing.DatasetSuiteBase
//import org.apache.log4j.{Level, Logger}
//import org.apache.spark.sql.{SaveMode, SparkSession}
//import org.scalatest.mockito.MockitoSugar
//import org.scalatest.{GivenWhenThen, Matchers, fixture}
//​
//class CountryFilesSpec extends fixture.FeatureSpec
//  with GivenWhenThen
//  with Matchers
//  with FilesystemOutputFixture
//  with DatasetSuiteBase
//  with SparkReaders
//  with MockitoSugar {
//  ​
//  implicit lazy val sparkSession: SparkSession = spark
//  ​
//  import spark.implicits._
//  ​
//  Logger.getLogger("org").setLevel(Level.OFF)
//  Logger.getLogger("akka").setLevel(Level.OFF)
//  ​
//  println(TestUtils.pathOfResource("/Tests/").toString)
//  val root = TestUtils.pathOfResource("/Tests/").toString
//  ​
//  ​
//  //  val timeColumn = TestUtils.pathOfResource("/rawdata/vidWrongTerminationType").toString
//  //  val metaInDirectory = TestUtils.pathOfResource("/rawdata/vidWrongTerminationType").toString
//  //  val metaOutDirectory = TestUtils.pathOfResource("/rawdata/vidWrongTerminationType").toString
//  ​
//  ​
//  feature("General checks related to information consistency") {
//    //
//    scenario("When a VID is not found on the lookUp Table, its country stays the same") { params =>
//      //
//      ​
//      Given("VID")
//      ​
//      ​
//      When("Given VID does not exists in the LookUp Table")
//      ​
//      ​
//      Then("The country should stay the same, unchanged, even when it is NULL")
//      ​
//      //BSHK0P.AWSHCI.HC.1.20180101094930
//      val cdb1 = Seq(("1234", "FR00", "2018-01-01T09:49:30"),
//        ("1234", "GB00", "20190101094930"),
//        ("456", "FR00", "20180501094930"),
//        ("789", "DE00", "20190201094930")
//      ).toDF("VID", "COUNTRY", "DATE")
//      ​
//      cdb1.coalesce(1)
//        .write
//        .option("header",true)
//        .option("delimiter",";")
//        .mode("Overwrite")
//        .parquet(root + "/cdb1.parquet")
//      ​
//      ​
//      val cdb2 = Seq(("1234", "FR00", "2019-01-01T09:49:30"),
//        ("1234", "GB00", "20190201094930"),
//        ("456", "DE00", "20190501094930")
//      ).toDF("VID", "COUNTRY", "DATE")
//      ​
//      cdb2.coalesce(1)
//        .write
//        .option("header",true)
//        .option("delimiter",";")
//        .mode("Overwrite")
//        .parquet(root + "/cdb2.parquet")
//      ​
//      ​
//      val lookUpTable = Seq(("1234", "FR", "20190520094930", "20190620094930","1"),
//        ("1234", "FR", "20190720094930", "20190728094930","2"),
//        ("456", "DE", "20180501094930", "20180501094930","1"),
//        ("789", "DE00", "20190201094930", "20180501094930","1")
//      ).toDF("VID", "COUNTRY", "VALIDFROM", "VALIDTO","ORDERNUMBER")
//      ​
//      lookUpTable.coalesce(1)
//        .write
//        .option("header",true)
//        .option("delimiter",";")
//        .mode("Overwrite")
//        .csv(root + "/lookup.csv").toString
//      ​
//      ​
//      var partitions = Set.empty[String]
//      Files.walkFileTree(Paths.get(root), new SimpleFileVisitor[Path] {
//        override def postVisitDirectory(dir: Path, exc: IOException): FileVisitResult = {
//          val filename = dir.getFileName.toString
//          val dirname = dir.toString
//          if (filename.contains("parquet") && filename.contains("cdb")) {
//            val partitionName = dirname
//            partitions = partitions + partitionName
//          }
//          FileVisitResult.CONTINUE
//        }
//      })
//      ​
//      val dirList = partitions.toList
//      ​
//      dirList.toDF("FILE")
//        .coalesce(1)
//        .write.mode("Overwrite")
//        .csv(root + "/manifest.csv").toString
//      ​
//      ​
//      new CountryUpdateLogic(root + "/manifest.csv",
//        root + "/lookup.csv",
//        root + "/TempDir/",
//        "DATE",
//        params.metaInDirectory.toString,
//        params.metaOutDirectory.toString,
//        Option(root + "/outTest/")).run()
//      ​
//      ​
//      //      class CountryUpdateLogic(val manifestPath: String,
//      //                               val cdbTimeTablePath: String,
//      //                               val tempDirPath: String,
//      //                               val timeColumn: String,
//      //                               val metaInDirectory: String,
//      //                               val metaOutDirectory: String,
//      //                               val outputPath: Option[String])
//      //                              (implicit val spark: SparkSession)
//      ​
//      1 shouldBe (1)
//      ​
//    }
//    ​
//  }
//  ​
//  ​
//}
//
//
