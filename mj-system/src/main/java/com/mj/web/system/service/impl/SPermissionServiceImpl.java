package com.mj.web.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mj.framework.service.AbstractService;
import com.mj.framework.util.ValueUtil;
import com.mj.web.system.domain.dobj.SPermissionDO;
import com.mj.web.system.domain.dto.SPermissionDTO;
import com.mj.web.system.domain.dto.response.SMenuPermissionRspDTO;
import com.mj.web.system.mapper.SPermissionMapper;
import com.mj.web.system.service.SPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class SPermissionServiceImpl extends AbstractService<SPermissionMapper, SPermissionDO, SPermissionDTO> implements SPermissionService {
    @Override
    public List<SMenuPermissionRspDTO> findMenuPermissionTree(SPermissionDTO request) {
        List<SPermissionDO> allMenu = list(new LambdaQueryWrapper<SPermissionDO>().eq(SPermissionDO::getActive, true).orderByDesc(SPermissionDO::getSort));
        if (CollectionUtils.isEmpty(allMenu)) {
            return null;
        }
        Map<String, List<SMenuPermissionRspDTO>> map = new HashMap<>();
        int level = allMenu.stream().max(Comparator.comparingInt(SPermissionDO::getLevel)).get().getLevel();
        // Map<String, List<SPermissionDO>> menuPermissionMap = sMenuPermissionService.menuPermissionMap();
        List<SMenuPermissionRspDTO> collect;
        for (int i = 1; i <= level; i++) {
            int finalI = i;
            collect = allMenu.stream()
                    .filter(menu -> menu.getLevel() == finalI)
                    .map(v -> {
                        SMenuPermissionRspDTO dump = ValueUtil.dump(v, SMenuPermissionRspDTO.class);
                        if (allMenu.stream().filter(m -> null != m.getParentId() && m.getParentId().equals(v.getId())).findFirst().isPresent()) {
                            List<SMenuPermissionRspDTO> sMenuPermissionRspDTOS = ValueUtil.dumpList(allMenu.stream()
                                    .filter(m -> null != m.getParentId() && m.getParentId().equals(v.getId())).collect(Collectors.toList()), SMenuPermissionRspDTO.class);
                            // sMenuPermissionRspDTOS.sort(Comparator.comparing(SMenuPermissionRspDTO::getSort));
                            dump.setChildren(sMenuPermissionRspDTOS);
                        }
                        return dump;
                    })
                    .collect(Collectors.toList());
            // collect.sort(Comparator.comparing(SMenuPermissionRspDTO::getSort));
            map.put("level" + i, collect);
        }
        for (int i = 1; i <= level; i++) {
            for (int j = i + 1; j <= level; ) {
                map.get("level" + i)
                        .forEach(item -> item.setChildren(map.get("level" + j).stream()
                                .filter((f) -> f.getParentId().equalsIgnoreCase(item.getId())).collect(Collectors.toList())));
                break;
            }
        }
        return map.get("level" + 1);
    }

    @Override
    public List<SPermissionDO> findAllMenu() {
        return baseMapper.selectList(new QueryWrapper<SPermissionDO>().in("type", 0, 1));
    }

    @Override
    public List<SPermissionDO> findByType(Integer type) {
        return baseMapper.selectList(new QueryWrapper<SPermissionDO>().eq("type", type));
    }
}
