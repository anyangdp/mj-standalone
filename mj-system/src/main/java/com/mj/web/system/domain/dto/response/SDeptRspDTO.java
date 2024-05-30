package com.mj.web.system.domain.dto.response;

import com.mj.framework.handler.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "SDeptRspDTO")
@Accessors(chain = true)
@Data
public class SDeptRspDTO extends AbstractDTO<String> {
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "父id")
    private String parentId;
    @ApiModelProperty(value = "子部门")
    private List<SDeptRspDTO> children = new ArrayList<>();

    public void addChild(SDeptRspDTO child) {
        children.add(child);
    }
}
