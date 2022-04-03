package org.apache.spark.sql.execution.benchmark

/**
 * Created with IDEA
 * Creater: MOBIN
 * Date: 2022/3/25
 * Time: 3:52 下午
 */
import java.util.Locale


class TPCDSQueryBenchmarkArguments(val args: Array[String]) {
  var dataLocation: String = null
  var queryFilter: Set[String] = Set.empty
  var cboEnabled: Boolean = false
  var iceberg: Boolean = false

  parseArgs(args.toList)
  validateArguments()

  private def optionMatch(optionName: String, s: String): Boolean = {
    optionName == s.toLowerCase(Locale.ROOT)
  }

  private def parseArgs(inputArgs: List[String]): Unit = {
    var args = inputArgs

    while (args.nonEmpty) {
      args match {
        case optName :: value :: tail if optionMatch("--data-location", optName) =>
          dataLocation = value
          args = tail

        case optName :: value :: tail if optionMatch("--query-filter", optName) =>
          queryFilter = value.toLowerCase(Locale.ROOT).split(",").map(_.trim).toSet
          args = tail

        case optName :: tail if optionMatch("--cbo", optName) =>
          cboEnabled = true
          args = tail

        case optName :: tail if optionMatch("--iceberg", optName) =>
          iceberg = true
          args = tail

        case _ =>
          // scalastyle:off println
          System.err.println("Unknown/unsupported param " + args)
          // scalastyle:on println
          printUsageAndExit(1)
      }
    }
  }

  private def printUsageAndExit(exitCode: Int): Unit = {
    // scalastyle:off
    System.err.println("""
                         |Usage: spark-submit --class <this class> <spark sql test jar> [Options]
                         |Options:
                         |  --data-location      Path to TPCDS data
                         |  --query-filter       Queries to filter, e.g., q3,q5,q13
                         |  --cbo                Whether to enable cost-based optimization
                         |  --iceberg            iceberg table
                         |
                         |------------------------------------------------------------------------------------------------------------------
                         |In order to run this benchmark, please follow the instructions at
                         |https://github.com/databricks/spark-sql-perf/blob/master/README.md
                         |to generate the TPCDS data locally (preferably with a scale factor of 5 for benchmarking).
                         |Thereafter, the value of <TPCDS data location> needs to be set to the location where the generated data is stored.
      """.stripMargin)
    // scalastyle:on
    System.exit(exitCode)
  }

  private def validateArguments(): Unit = {
    if (dataLocation == null) {
      // scalastyle:off println
      System.err.println("Must specify a data location")
      // scalastyle:on println
      printUsageAndExit(-1)
    }
  }
}