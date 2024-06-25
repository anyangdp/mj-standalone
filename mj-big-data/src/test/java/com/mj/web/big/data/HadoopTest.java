package com.mj.web.big.data;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URI;


public class HadoopTest {

    @Test
    void listFolder() throws IOException {
        String hdfsUri = "hdfs://namenode:8020"; // HDFS的URI，根据你的配置可能不同
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", hdfsUri);
        FileSystem hdfs = FileSystem.get(conf);

        Path listPath = new Path("/"); // 要列出文件的目录
        FileStatus[] files = hdfs.listStatus(listPath);
        for (FileStatus file : files) {
            System.out.println(file.getPath());
        }

        hdfs.close();
    }

    /**
     * 读取文件内容
     * @throws IOException
     */
    @Test
    void readFile() throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://localhost:8020");
        conf.set("dfs.client.use.datanode.hostname", "true");
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        FileSystem fs = FileSystem.get(conf);
        try {
            // 定义HDFS文件路径
            Path hdfsPath = new Path("/tmp/age.csv"); // 替换为你要读取的文件路径

            // 使用BufferedReader读取文件内容
            try (BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(hdfsPath)))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭输入流
            fs.close();
        }
    }


    /**
     * 本地文件上传 create
     * @throws IOException
     */
    @Test
    void writeFile() throws IOException {
        // HDFS地址
        String hdfsUri = "hdfs://namenode:8020"; // 根据实际情况修改HDFS的地址和端口
        // 本地文件路径
        String localFilePath = "E:\\github\\mj-standalone\\mj-big-data\\src\\test\\java\\com\\mj\\web\\big\\data\\uploadedFile.txt"; // 替换为你的本地文件路径
        // HDFS目标路径
        String hdfsPath = "/tmp/uploadedFile4.txt"; // 替换为你希望在HDFS上的保存路径和文件名
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        try {
            // 配置Hadoop环境
            Configuration conf = new Configuration();
            conf.set("dfs.replication", "1");
            conf.set("dfs.client.use.datanode.hostname", "true");
            FileSystem fs = FileSystem.get(URI.create(hdfsUri), conf);
            // 创建输入流读取本地文件
            FileInputStream fis = new FileInputStream(localFilePath);
            // 在HDFS上创建输出流准备写入
            FSDataOutputStream fos = fs.create(new Path(hdfsPath));
            // 读取本地文件并写入HDFS
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            // 关闭流
            fos.close();
            fis.close();

            // 关闭FileSystem资源
            fs.close();
            System.out.println("文件上传到HDFS成功！");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("文件上传失败：" + e.getMessage());
        }
    }

    /**
     * 删除文件
     * @throws IOException
     */
    @Test
    void deleteFile() throws IOException {
        String hdfsUri = "hdfs://localhost:9000"; // HDFS的URI，根据你的配置可能不同
        System.setProperty("HADOOP_USER_NAME", "root");
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", hdfsUri);
        FileSystem hdfs = FileSystem.get(conf);
        boolean delete = hdfs.delete(new Path("/tmp/file.csv"), false);
        System.out.println("删除结果："+ delete);
        hdfs.close();
    }

    /**
     * 上传文件copyFromLocalFile
     * @throws IOException
     */
    @Test
    void uploadCSVFile() throws IOException {
        String hdfsUri = "hdfs://namenode:8020"; // HDFS的URI，根据你的配置可能不同
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", hdfsUri);
        conf.set("dfs.replication", "1");
        conf.set("dfs.client.use.datanode.hostname", "true");
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        FileSystem fs = FileSystem.get(conf);
        // 本地文件路径
        String localFilePath = "E:\\github\\mj-standalone\\mj-big-data\\src\\main\\resources\\sample\\age.csv";
        // HDFS目标路径
        String hdfsTargetPath = "hdfs://localhost:8020/tmp/age.csv";
        // 上传文件
        fs.copyFromLocalFile(new Path(localFilePath), new Path(hdfsTargetPath));
        System.out.println("File uploaded successfully to HDFS.");

        fs.close();
    }

    @Test
    void downloadFile() throws IOException {
        String hdfsUri = "hdfs://namenode:8020"; // HDFS的URI，根据你的配置可能不同
        String hdfsFilePath = "/tmp/20240514161505.png";
        String localFilePath = "E:\\github\\mj-standalone\\mj-big-data\\src\\test\\java\\com\\mj\\web\\big\\data\\20240514161505.png";
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", hdfsUri);
        conf.set("dfs.replication", "1");
        conf.set("dfs.client.use.datanode.hostname", "true");
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        // 本地文件路径
        // HDFS目标路径
        // 创建 FileSystem 对象
        try (FileSystem fs = FileSystem.get(URI.create(hdfsUri), conf);
             InputStream in = fs.open(new Path(hdfsFilePath));
             FileOutputStream out = new FileOutputStream(localFilePath)) {

            // 读取 HDFS 文件并写入本地文件
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            System.out.println("文件下载成功！");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件下载失败！");
        }
    }
}
