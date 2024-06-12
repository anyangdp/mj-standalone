package com.mj.web.big.data.domain.bo.db;

import com.mj.web.big.data.enums.DatasourceTypeEnum;

public class PostgresqlDataSourceProperties extends JdbcDataSourceProperties{
    public PostgresqlDataSourceProperties(String url, String driver, String username, String password) {
        super(url, driver, username, password);
    }
    @Override
    public DatasourceTypeEnum getDatasourceType() {
        return DatasourceTypeEnum.POSTGRESQL;
    }
}
