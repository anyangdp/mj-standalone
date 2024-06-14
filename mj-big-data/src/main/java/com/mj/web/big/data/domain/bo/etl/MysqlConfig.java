package com.mj.web.big.data.domain.bo.etl;

import com.mj.web.big.data.enums.DataSourceTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MysqlConfig extends JdbcConfig{
    @Override
    public String getType() {
        return DataSourceTypeEnum.MYSQL.name();
    }
}
