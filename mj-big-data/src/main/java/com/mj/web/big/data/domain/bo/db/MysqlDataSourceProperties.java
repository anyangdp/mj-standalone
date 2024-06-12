package com.mj.web.big.data.domain.bo.db;

import com.mj.web.big.data.enums.DatasourceTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Accessors(chain = true)
@Setter
@Getter
public class MysqlDataSourceProperties extends JdbcDataSourceProperties {
    public MysqlDataSourceProperties(String username, String password, String driver, String url) {
        super(username, password, driver, url);
    }

    public final String SHOW_TABLE = "show tables";
    @Override
    public DatasourceTypeEnum getDatasourceType() {
        return DatasourceTypeEnum.MYSQL;
    }

    @Override
    public Connection createConnection() throws SQLException {
        log.info("url:{},username:{},password:{},driver:{}",getUrl(),getUsername(),getPassword(),getDriverClassName());
        return DataSourceBuilder.create()
                .url(getUrl())
                .username(getUsername())
                .password(getPassword())
                .driverClassName(getDriverClassName())
                .build().getConnection();
    }
}
