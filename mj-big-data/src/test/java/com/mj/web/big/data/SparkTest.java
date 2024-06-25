package com.mj.web.big.data;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.Test;
import scala.Tuple2;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.List;


public class SparkTest {

    @Test
    public void test1() {
        SparkConf sparkConf = new SparkConf()
                .setAppName("MyApp")
                .setMaster("spark://localhost:7077"); // 替换为你的Spark Master IP地址

        SparkSession spark = SparkSession.builder().config(sparkConf).getOrCreate();
    }

    @Test
    public void csvReadAndWrite() {
        SparkConf sparkConf = new SparkConf()
                .setAppName("csv-read-write")
                .setMaster("spark://localhost:7077"); // 替换为你的Spark Master IP地址

        SparkSession spark = SparkSession.builder().config(sparkConf).getOrCreate();
        // 读取本地 CSV 文件
        Dataset<Row> df = spark.read().option("header", "true").csv("/opt/test.csv");

        // 打印读取的数据
        df.show();

        // 将数据保存到 HDFS
        df.write().parquet("hdfs://localhost:9000/path/to/output/");

        // 关闭 Spark 会话
        spark.stop();
    }

}
