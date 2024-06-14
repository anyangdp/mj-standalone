package com.mj.web.big.data.domain.bo.etl;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class JdbcConfig extends Configuration{
    private String column;

}
