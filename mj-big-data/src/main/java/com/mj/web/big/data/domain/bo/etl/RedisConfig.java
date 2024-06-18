package com.mj.web.big.data.domain.bo.etl;

import com.mj.web.big.data.enums.DataSourceTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RedisConfig extends Configuration{
    @Override
    public String getType() {
        return DataSourceTypeEnum.REDIS.name();
    }
    private String column;
    private String mode;
}
