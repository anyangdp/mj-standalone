package com.mj.web.big.data.domain.bo.db;

import com.mj.web.big.data.enums.DataSourceTypeEnum;

public class PostgresqlDataSourceProperties extends JdbcDataSourceProperties{
    public PostgresqlDataSourceProperties(String url, String driver, String username, String password) {
        super(url, driver, username, password);
    }
    @Override
    public DataSourceTypeEnum getDatasourceType() {
        return DataSourceTypeEnum.POSTGRESQL;
    }
}
