package com.mj.web.big.data.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jdbc.DataSourceBuilder;

import java.sql.Connection;
import java.sql.SQLException;

@Setter
@Getter
@AllArgsConstructor
public abstract class JdbcDataSourceProperties implements DataSourceProperties {
    private String username;
    private String password;
    private String driverClassName;
    private String url;

    @Override
    public Connection createConnection() throws SQLException {
        return DataSourceBuilder.create()
                .url(getUrl())
                .username(getUsername())
                .password(getPassword())
                .driverClassName(getDriverClassName())
                .build().getConnection();
    }
}
