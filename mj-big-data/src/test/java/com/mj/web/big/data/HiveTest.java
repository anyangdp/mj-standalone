package com.mj.web.big.data;

import org.apache.hive.jdbc.HiveDriver;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HiveTest {
    @Test
    void test1() {
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName(HiveDriver.class.getName())
                .url("jdbc:hive2://localhost:10003/default")
                .username("")
                .password("")
                .build();
        try {
            Connection connection = dataSource.getConnection();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS test_table (id INT, name STRING)").execute();
            connection.prepareStatement("INSERT INTO test_table (id, name) VALUES (1, 'John Doe')").execute();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testQuery() {
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName(HiveDriver.class.getName())
                .url("jdbc:hive2://localhost:10003/default")
                .username("")
                .password("")
                .build();
        try {
            Connection connection = dataSource.getConnection();
            ResultSet resultSet = connection.prepareStatement("SELECT * FROM test_table").executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
