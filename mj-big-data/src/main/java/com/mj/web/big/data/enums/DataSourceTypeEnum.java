package com.mj.web.big.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataSourceTypeEnum {
    MYSQL("mysql"),
    ORACLE("oracle"),
    SQLSERVER("sqlserver"),
    POSTGRESQL("postgresql"),
    HIVE("hive"),
    HBASE("hbase"),
    CASSANDRA("cassandra"),
    REDIS("redis"),
    MONGODB("mongodb");
    private final String des;

    public static DataSourceTypeEnum lookup(String name) {
        switch (name) {
            case "MYSQL":
                return MYSQL;
            case "ORACLE":
                return ORACLE;
            case "SQLSERVER":
                return SQLSERVER;
            case "POSTGRESQL":
                return POSTGRESQL;
            case "HIVE":
                return HIVE;
            case "HBASE":
                return HBASE;
            case "CASSANDRA":
                return CASSANDRA;
            case "REDIS":
                return REDIS;
            case "MONGODB":
                return MONGODB;
            default:
                return null;
        }
    }
}
