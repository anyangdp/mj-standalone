package com.mj.web.big.data;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;


public class HadoopTest {
    private FileSystem fileSystem() throws IOException {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFs", "hdfs://localhost:9000");
        return FileSystem.get(URI.create("hdfs://localhost:9000"), configuration);
    }
    public void createFile(String filePath, InputStream inputStream) throws IOException {
        Path path = new Path(filePath);
        try (OutputStream outputStream = fileSystem().create(path)) {
            IOUtils.copyBytes(inputStream, outputStream, 4096, true);
        }
    }

    public InputStream readFile(String filePath) throws IOException {
        Path path = new Path(filePath);
        return fileSystem().open(path);
    }

    public void deleteFile(String filePath) throws IOException {
        Path path = new Path(filePath);
        fileSystem().delete(path, true);
    }
    @Test
    public void testHdfsOperations() throws IOException {
        String filePath = "/test-file.txt";
        String fileContent = "Hello, HDFS!";
        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes());

        // 创建文件
        createFile(filePath, inputStream);

        // 读取文件
        InputStream readFileInputStream = readFile(filePath);
        byte[] buffer = new byte[fileContent.length()];
        readFileInputStream.read(buffer);
        String readFileContent = new String(buffer);

        System.out.println("文件内容："+ readFileContent);
        // 删除文件
        deleteFile(filePath);
    }

    @Test
    void test2() throws IOException {
        String hdfsUri = "hdfs://localhost:9000"; // HDFS的URI，根据你的配置可能不同
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
}
