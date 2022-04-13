This is a TPCDS data generator and queries benchmark for Apache Spark,It also support iceberg data, which is fork from [spark-tpcds-datagen](https://github.com/maropu/spark-tpcds-datagen)

Note that the current `master` branch intends to support [3.2.1](https://downloads.apache.org/spark/spark-3.2.1) on Scala 2.12.x.
## How to generate TPCDS data

You can generate TPCDS data in `/tmp/spark-tpcds-data`:

    # You need to set `SPARK_HOME` to your Spark v3.2.1 path before running a command below
    $ ./bin/dsdgen --output-location /tmp/spark-tpcds-data [--iceberg]

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

## Options for the queries
    Options:
      --data-location      Path to TPCDS data
      --query-filter       Queries to filter, e.g., q3,q5,q13
      --cbo                Whether to enable cost-based optimization
      --iceberg            Whether queries iceberg tables

## Run specific TPCDS quries only

To run a part of TPCDS queries, you type:

    $ ./bin/run-tpcds-benchmark --data-location [TPCDS data/Iceberg TPCDS data] --query-filter "q2,q5" [--iceberg]