package com.mj.web.big.data.domain.bo.db;

import com.mj.web.big.data.enums.DataSourceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Connection;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class JdbcConnection implements Serializable {
    private static final long serialVersionUID = 4371206474706286410L;
    private DataSourceTypeEnum datasourceTypeEnum;
    private Connection connection;
}
