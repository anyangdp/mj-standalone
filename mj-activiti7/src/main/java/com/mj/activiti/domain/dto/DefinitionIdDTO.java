package com.mj.activiti.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.activiti.engine.repository.ProcessDefinition;

import java.io.Serializable;

@Setter
@Getter
public class DefinitionIdDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String deploymentID;
    private String resourceName;

    public DefinitionIdDTO() {
    }

    public DefinitionIdDTO(String deploymentID, String resourceName) {
        this.deploymentID = deploymentID;
        this.resourceName = resourceName;
    }
}
