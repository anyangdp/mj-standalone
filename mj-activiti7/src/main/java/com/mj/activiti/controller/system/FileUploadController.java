package com.mj.activiti.controller.system;

import com.mj.common.util.SnowFlake;
import com.mj.framework.handler.ControllerTemplate;
import com.mj.framework.handler.GenericResponse;
import com.mj.framework.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Author anyang
 * @CreateTime 2022/4/1
 * @Des
 */
@Api(value = "文件上传接口", tags = "文件上传接口")
@RestController
@RequestMapping("/s/file")
public class FileUploadController {
    @Autowired
    private SnowFlake snowFlake;

    //绑定文件上传路径到uploadPath
    @Value("${web.upload-path}")
    private String uploadPath;

    // @ApiOperation(value = "文件上传")
    // @PostMapping(value = "/upload")
    @Deprecated
    public GenericResponse<String> uploadToClasspath(MultipartFile file) throws Exception {
        return ControllerTemplate.call(response -> {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String realPath = ResourceUtils.getURL("classpath:").getPath() + "static";
            String name = snowFlake.nextIdStr() + "." + suffix;
            response.setResult(FileUtil.createFile(file, file.getName(), realPath+"/upload/", name));
            response.setData(ServletUriComponentsBuilder.fromCurrentContextPath().path("/upload/").path(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).path("/").path(name).toUriString());
        });
    }
    @ApiOperation(value = "文件上传")
    @PostMapping(value = "/upload")
    public GenericResponse<String> upload(@RequestPart("file") MultipartFile file) throws Exception {
        return ControllerTemplate.call(response -> {
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            File folder = new File(uploadPath + date);
            if (!folder.isDirectory()) {
                folder.mkdirs();
            }
            // 对上传的文件重命名，避免文件重名
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String name = snowFlake.nextIdStr() + "." + suffix;
            try {
                file.transferTo(new File(folder, name));
                response.setResult(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 返回上传文件的访问路径
            response.setData(ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/").path(date).path("/").path(name).toUriString());
        });
    }

}
