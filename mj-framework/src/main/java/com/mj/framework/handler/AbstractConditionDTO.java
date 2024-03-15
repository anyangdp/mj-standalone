package com.mj.framework.handler;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author anyang
 * @CreateTime 2019/10/9
 * @Des 底层通用抽象实体DTO
 */
@Accessors(chain = true)
@Data
public abstract class AbstractConditionDTO<PK> extends AbstractDTO {
    private static final long serialVersionUID = -5756045370916112553L;

    private String companyId;

    private List<String> lotIdList;
}
