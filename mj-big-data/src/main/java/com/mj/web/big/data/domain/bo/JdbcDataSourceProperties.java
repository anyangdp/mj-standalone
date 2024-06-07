package com.mj.web.big.data.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public abstract class JdbcDataSourceProperties implements DataSourceProperties {
    private String username;
    private String password;
    private String driverClassName;
    private String url;
}
