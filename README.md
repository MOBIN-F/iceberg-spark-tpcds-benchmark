This is a TPCDS data generator for Apache Spark,It also support generate iceberg data, which is fork from [spark-tpcds-datagen](https://github.com/maropu/spark-tpcds-datagen)

Note that the current `master` branch intends to support [3.2.1](https://downloads.apache.org/spark/spark-3.2.1) on Scala 2.12.x.
## How to generate TPCDS data

You can generate TPCDS data in `/tmp/spark-tpcds-data`:

    # You need to set `SPARK_HOME` to your Spark v3.2.1 path before running a command below
    $ ./bin/dsdgen --output-location /tmp/spark-tpcds-data
    $ or ./bin/dsdgen --output-location /tmp/spark-tpcds-data --iceberg

## How to run TPCDS queries in Spark

If you run TPCDS quries on the master branch of Spark, you say a sequence of commands below:

    $ git clone https://github.com/apache/spark.git

    $ cd spark && ./build/mvn clean package -DskipTests

    $ ./bin/spark-submit \
        --class org.apache.spark.sql.execution.benchmark.TPCDSQueryBenchmark \
        --jars ${SPARK_HOME}/core/target/spark-core_<scala.version>-<spark.version>-tests.jar,${SPARK_HOME}/sql/catalyst/target/spark-catalyst_<scala.version>-<spark.version>-tests.jar \
        ${SPARK_HOME}/sql/core/target/spark-sql_<scala.version>-<spark.version>-tests.jar \
        --data-location /tmp/spark-tpcds-data

## Options for the generator

    $ ./bin/dsdgen --help
    Usage: spark-submit --class <this class> --conf key=value <spark tpcds datagen jar> [Options]
    Options:
      --output-location [STR]                Path to an output location
      --scale-factor [NUM]                   Scale factor (default: 1)
      --format [STR]                         Output format (default: parquet)
      --overwrite                            Whether it overwrites existing data (default: false)
      --iceberg                              Whether it generate iceberg data (default: false)
      --partition-tables                     Whether it partitions output data (default: false)
      --use-double-for-decimal               Whether it prefers double types instead of decimal types (default: false)
      --use-string-for-char                  Whether it prefers string types instead of char/varchar types (default: false)
      --cluster-by-partition-columns         Whether it cluster output data by partition columns (default: false)
      --filter-out-null-partition-values     Whether it filters out NULL partitions (default: false)
      --table-filter [STR]                   Queries to filter, e.g., catalog_sales,store_sales
      --num-partitions [NUM]                 # of partitions (default: 100)

## Run specific TPCDS quries only

To run a part of TPCDS queries, you type:

    $ ./bin/run-tpcds-benchmark --data-location [TPCDS data] --query-filter "q2,q5"

## Other helper scripts for benchmarks

To quickly generate the TPCDS data and run the queries, you just type:

    $ ./bin/report-tpcds-benchmark [TPCDS data] [output file]

This script finally formats performance results and appends them into ./reports/tpcds-avg-results.csv.
Notice that, if `SPARK_HOME` defined, the script uses the Spark.
Otherwise, it automatically clones the latest master in the repository and uses it.
To check performance differences with pull requests, you could set a pull request ID in the repository as an option
and run the quries against it.

    $ ./bin/report-tpcds-benchmark [TPCDS data] [output file] [pull request ID (e.g., 12942)]

