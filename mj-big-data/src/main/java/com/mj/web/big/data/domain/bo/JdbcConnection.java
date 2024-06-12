package com.mj.web.big.data.domain.bo;

import com.mj.web.big.data.enums.DatasourceTypeEnum;
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
    private DatasourceTypeEnum datasourceTypeEnum;
    private Connection connection;
}
