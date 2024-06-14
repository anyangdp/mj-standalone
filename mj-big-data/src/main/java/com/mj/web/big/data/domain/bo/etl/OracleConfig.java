package com.mj.web.big.data.domain.bo.etl;

import com.mj.web.big.data.enums.DataSourceTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OracleConfig extends JdbcConfig{
    @Override
    public String getType() {
        return DataSourceTypeEnum.ORACLE.name();
    }
}
