package com.mj.web.big.data.domain.bo.etl;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EtlConfiguration {
    private Configuration origin;
    private Configuration target;
}
