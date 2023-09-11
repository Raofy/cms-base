package com.test.service.impl;

import com.test.entity.LinPermission;
import com.test.mapper.LinPermissionMapper;
import com.test.service.ILinPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Rao
 * @since 2023/09/01 17:35
 */
@Service
public class LinPermissionServiceImpl extends ServiceImpl<LinPermissionMapper, LinPermission> implements ILinPermissionService {

    @Override
    public List<LinPermission> getPermissionByGroupIds(List<Long> groupIds) {
        List<LinPermission> permission = baseMapper.getPermissionByGroupIds(groupIds);
        return permission;
    }
}
