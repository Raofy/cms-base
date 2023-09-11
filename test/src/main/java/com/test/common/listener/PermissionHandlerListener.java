package com.test.common.listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rfy.bean.MetaInfo;
import com.rfy.bean.PermissionMetaRouter;
import com.test.entity.LinPermission;
import com.test.service.ILinPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 将权限菜单持久化 permission 表中
 *
 * Created by Rao on 2023/9/1 17:47
 */
@Component
public class PermissionHandlerListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    ILinPermissionService iLinPermissionService;
    @Autowired
    PermissionMetaRouter metaRouter;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        addNewPermission();
        removeUnusedPermission();
    }

    private void addNewPermission() {
        metaRouter.getMetaInfoMap().values().forEach(metaInfo -> {
            String module = metaInfo.getModule();
            String permission = metaInfo.getPermission();
            createPermissionIfNotExit(module, permission);
        });
    }

    private void removeUnusedPermission() {
        List<LinPermission> list = iLinPermissionService.list();
        Map<String, MetaInfo> metaInfoMap = metaRouter.getMetaInfoMap();
        for (LinPermission permission : list) {
            boolean match = metaInfoMap.values().stream().anyMatch(item -> item.getModule().equals(permission.getModule()) && item.getPermission().equals(permission.getName()));
            if (!match) {
                permission.setMount(false);
                iLinPermissionService.updateById(permission);
            }
        }
    }

    private void createPermissionIfNotExit(String module, String permission) {
        LambdaQueryWrapper<LinPermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LinPermission::getModule, module).eq(LinPermission::getName, permission);
        LinPermission one = iLinPermissionService.getOne(queryWrapper);
        if (one == null) {
            LinPermission linPermission = new LinPermission();
            linPermission.setName(permission);
            linPermission.setModule(module);
            iLinPermissionService.save(linPermission);
        } else {
            if (one.getMount() == false) {
                one.setMount(true);
                iLinPermissionService.updateById(one);
            }
        }
    }
}
