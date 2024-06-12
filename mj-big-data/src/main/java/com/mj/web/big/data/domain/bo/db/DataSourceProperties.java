package com.mj.web.big.data.domain.bo.db;

import com.mj.web.big.data.enums.DatasourceTypeEnum;
import lombok.experimental.Accessors;

import java.sql.Connection;
import java.sql.SQLException;


@Accessors(chain = true)
public interface DataSourceProperties {
    DatasourceTypeEnum getDatasourceType();

    String getUsername();

    String getPassword();

    String getDriverClassName();

    String getUrl();

    Connection createConnection() throws SQLException;
}