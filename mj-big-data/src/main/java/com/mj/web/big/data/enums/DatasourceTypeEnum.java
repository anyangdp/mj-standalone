package com.mj.web.big.data.enums;

import com.mj.web.big.data.domain.bo.DataSourceProperties;
import com.mj.web.big.data.domain.bo.MysqlDataSourceProperties;
import com.mj.web.big.data.domain.bo.PostgresqlDataSourceProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Supplier;

@Getter
@AllArgsConstructor
public enum DatasourceTypeEnum {
    MYSQL("mysql"),
    ORACLE("oracle"),
    SQLSERVER("sqlserver"),
    POSTGRESQL("postgresql"),
    DB2("db2"),
    HIVE("hive"),
    HBASE("hbase"),
    CASSANDRA("cassandra"),
    REDIS("redis"),
    MONGODB("mongodb");
    private final String name;
}
