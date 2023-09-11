package com.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.entity.LinGroup;
import com.test.entity.LinPermission;
import com.test.entity.LinUser;
import com.test.mapper.LinUserMapper;
import com.test.service.ILinGroupService;
import com.test.service.ILinPermissionService;
import com.test.service.ILinUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Rao
 * @since 2023/09/01 17:17
 */
@Service
public class LinUserServiceImpl extends ServiceImpl<LinUserMapper, LinUser> implements ILinUserService{

    @Autowired
    ILinGroupService iLinGroupService;
    @Autowired
    ILinPermissionService iLinPermissionService;

    @Override
    public List<LinPermission> getUserPermission(LinUser user) {
        List<Long> groupIds = iLinGroupService.getGroupIdsById(user.getId());
        if (groupIds == null || groupIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<LinPermission> permissionByGroupIds = iLinPermissionService.getPermissionByGroupIds(groupIds);
        return permissionByGroupIds.isEmpty() ? new ArrayList<>() : permissionByGroupIds;
    }

    @Override
    public LinUser getByUsername(String username) {
        QueryWrapper<LinUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LinUser::getUsername, username);
        LinUser user = baseMapper.selectOne(queryWrapper);
        return user;
    }
}
