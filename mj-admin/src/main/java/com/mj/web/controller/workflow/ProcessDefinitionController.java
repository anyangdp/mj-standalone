package com.mj.web.controller.workflow;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mj.activiti.domain.dto.DefinitionIdDTO;
import com.mj.activiti.domain.dto.PageDomain;
import com.mj.activiti.domain.dto.ProcessDefinitionDTO;
import com.mj.activiti.service.IProcessDefinitionService;
import com.mj.common.exception.exception.BizException;
import com.mj.framework.handler.ControllerTemplate;
import com.mj.framework.handler.GenericResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/processDefinition")
public class ProcessDefinitionController {
    @Autowired
    private IProcessDefinitionService processDefinitionService;


    /**
     * 获取流程定义集合
     *
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping(value = "/page/{page}/{pageSize}")
    public GenericResponse<IPage<ProcessDefinitionDTO>> list(@PathVariable Integer page, @PathVariable Integer pageSize,
                                                 @RequestBody @Valid ProcessDefinitionDTO request) throws Exception {

        return ControllerTemplate.call(response -> {
            IPage<ProcessDefinitionDTO> processDefinitionDTOS = processDefinitionService.selectProcessDefinitionList(request, page, pageSize);
            response.setResult(true).setData(processDefinitionDTOS);
        });

    }

    @ApiOperation(value = "获取当前流程", notes = "获取当前流程")
    @GetMapping(value = "/getDefinitions/{instanceId}")
    public GenericResponse<DefinitionIdDTO> getDefinitionsByInstanceId(@PathVariable("instanceId") String instanceId) throws Exception {
        return ControllerTemplate.call(response -> {
            response.setResult(true).setData(processDefinitionService.getDefinitionsByInstanceId(instanceId));
        });
    }

    @ApiOperation(value = "删除流程定义", notes = "删除流程定义")
    @DeleteMapping(value = "/remove/{deploymentId}")
    public GenericResponse<Void> delDefinition(@PathVariable("deploymentId") String deploymentId) throws Exception {
        return ControllerTemplate.call(response -> {
            processDefinitionService.deleteProcessDefinitionById(deploymentId);
            response.setResult(true);
        });
    }

    @ApiOperation(value = "上传并部署流程定义", notes = "上传并部署流程定义")
    @PostMapping(value = "/uploadStreamAndDeployment")
    public GenericResponse<Void> uploadStreamAndDeployment(@RequestPart("file") MultipartFile file) throws Exception {
        return ControllerTemplate.call(response -> {
            processDefinitionService.uploadStreamAndDeployment(file);
            response.setResult(true);
        });
    }


    @ApiOperation(value = "启动挂起流程流程定义", notes = "启动挂起流程流程定义")
    @PostMapping("/suspendOrActiveApply")
    @ResponseBody
    public GenericResponse<Void> suspendOrActiveApply(@RequestBody ProcessDefinitionDTO processDefinition) throws Exception {
        return ControllerTemplate.call(response -> {
            processDefinitionService.suspendOrActiveApply(processDefinition.getId(), processDefinition.getSuspendState());
            response.setResult(true);
        });
    }

    @ApiOperation(value = "上传流程流程定义", notes = "上传流程流程定义")
    @PostMapping(value = "/upload")
    public GenericResponse<Void> upload(@RequestPart("processFile") MultipartFile multipartFile) throws Exception {
        return ControllerTemplate.call(response -> {
            if (!multipartFile.isEmpty()) {
                String fileName = processDefinitionService.upload(multipartFile);
                response.setResult(true);
                return;
            }
            throw new BizException("不允许上传空文件！");
        });
    }


    @ApiOperation(value = "通过stringBPMN添加流程定义", notes = "通过stringBPMN添加流程定义")
    @PostMapping(value = "/addDeploymentByString")
    public GenericResponse<Void> addDeploymentByString(@RequestParam("stringBPMN") String stringBPMN) throws Exception {
        processDefinitionService.addDeploymentByString(stringBPMN);
        return ControllerTemplate.call(response -> {
            processDefinitionService.addDeploymentByString(stringBPMN);
            response.setResult(true);
        });

    }


    @ApiOperation(value = "获取流程定义XML", notes = "获取流程定义XML")
    @GetMapping(value = "/getDefinitionXML")
    public void getProcessDefineXML(HttpServletResponse response,
                                    @RequestParam("deploymentId") String deploymentId,
                                    @RequestParam("resourceName") String resourceName) throws IOException {

        processDefinitionService.getProcessDefineXML(response, deploymentId, resourceName);
    }
}
