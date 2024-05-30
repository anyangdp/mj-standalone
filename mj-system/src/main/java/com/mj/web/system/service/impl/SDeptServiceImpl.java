package com.mj.web.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mj.framework.service.AbstractService;
import com.mj.framework.util.ValueUtil;
import com.mj.web.system.domain.dobj.SDeptDO;
import com.mj.web.system.domain.dobj.SUserDO;
import com.mj.web.system.domain.dto.SDeptDTO;
import com.mj.web.system.domain.dto.response.SDeptRspDTO;
import com.mj.web.system.service.SDeptService;
import com.mj.web.system.mapper.SDeptMapper;
import com.mj.web.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
@Transactional
public class SDeptServiceImpl extends AbstractService<SDeptMapper, SDeptDO, SDeptDTO> implements SDeptService {
    @Autowired
    private UserService userService;
    @Override
    public List<SDeptRspDTO> findTree(SDeptDTO request) throws Exception {

        LambdaQueryWrapper<SDeptDO> sDeptDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<SDeptDO> sDeptDOS = getBaseMapper().selectList(sDeptDOLambdaQueryWrapper.eq(SDeptDO::getActive, true).eq(SDeptDO::isDeleted, false));
        return buildTree(ValueUtil.dumpList(sDeptDOS, SDeptRspDTO.class));
    }

    private static List<SDeptRspDTO> buildTree(List<SDeptRspDTO> SDeptRspDTOs) {
        Map<String, SDeptRspDTO> deptMap = new HashMap<>();
        List<SDeptRspDTO> rootSDeptRspDTOs = new ArrayList<>();

        // 将所有部门存储到哈希表中
        for (SDeptRspDTO dept : SDeptRspDTOs) {
            deptMap.put(dept.getId(), dept);
        }

        // 构建树结构
        for (SDeptRspDTO dept : SDeptRspDTOs) {
            if (dept.getParentId().isEmpty()) {
                rootSDeptRspDTOs.add(dept);
            } else {
                SDeptRspDTO parent = deptMap.get(dept.getParentId());
                if (parent != null) {
                    parent.addChild(dept);
                }
            }
        }

        return rootSDeptRspDTOs;
    }

    private static void printTree(List<SDeptRspDTO> deptTree, int level) {
        for (SDeptRspDTO dept : deptTree) {
            for (int i = 0; i < level; i++) {
                System.out.print("  ");
            }
            System.out.println(dept.getName());
            printTree(dept.getChildren(), level + 1);
        }
    }
}
