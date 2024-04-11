package com.mj.activiti.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Setter
@Getter
public class ProcessDefinitionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String key;

    private int version;

    private String deploymentId;
    private String resourceName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deploymentTime;

    /** 流程实例状态 1 激活 2 挂起 */
    private Integer suspendState;

    public ProcessDefinitionDTO(ProcessDefinitionEntityImpl processDefinition) {
        this.id = processDefinition.getId();
        this.name = processDefinition.getName();
        this.key = processDefinition.getKey();
        this.version = processDefinition.getVersion();
        this.deploymentId = processDefinition.getDeploymentId();
        this.resourceName = processDefinition.getResourceName();

        this.suspendState = processDefinition.getSuspensionState();
    }
}
