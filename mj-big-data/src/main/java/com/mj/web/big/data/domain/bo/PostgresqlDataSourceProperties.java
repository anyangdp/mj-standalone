package com.mj.web.big.data.domain.bo;

import com.mj.web.big.data.enums.DatasourceTypeEnum;
import org.springframework.boot.jdbc.DataSourceBuilder;

import java.sql.Connection;
import java.sql.SQLException;

public class PostgresqlDataSourceProperties extends JdbcDataSourceProperties{
    public PostgresqlDataSourceProperties(String url, String driver, String username, String password) {
        super(url, driver, username, password);
    }
    @Override
    public DatasourceTypeEnum getDatasourceType() {
        return DatasourceTypeEnum.POSTGRESQL;
    }
}
