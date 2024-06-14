package com.mj.web.big.data.domain.bo.etl;

import com.mj.web.big.data.enums.DataSourceTypeEnum;

public class SqlserverConfig extends JdbcConfig{
    @Override
    public String getType() {
        return DataSourceTypeEnum.SQLSERVER.name();
    }
}
