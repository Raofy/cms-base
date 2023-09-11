package com.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.common.enumeration.GroupLevelEnum;
import com.test.entity.LinGroup;
import com.test.entity.LinUserGroup;
import com.test.mapper.LinGroupMapper;
import com.test.mapper.LinUserGroupMapper;
import com.test.service.ILinGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Rao
 * @since 2023/09/01 17:31
 */
@Service
public class LinGroupServiceImpl extends ServiceImpl<LinGroupMapper, LinGroup> implements ILinGroupService{

    @Autowired
    LinUserGroupMapper userGroupMapper;
    @Override
    public boolean checkIsRootByUserId(Long id) {
        LambdaQueryWrapper<LinUserGroup> queryWrapper = new LambdaQueryWrapper<>();
        Long rootGroupId = getParticularGroupIdByLevel(GroupLevelEnum.ROOT);
        queryWrapper.eq(LinUserGroup::getGroupId, rootGroupId).eq(LinUserGroup::getUserId, id);
        LinUserGroup rootUserGroup = userGroupMapper.selectOne(queryWrapper);
        return rootUserGroup != null;
    }

    @Override
    public List<Long> getGroupIdsById(Long id) {
        List<Long> groupIds = baseMapper.getGroupIdsById(id);
        return groupIds;
    }

    private Long getParticularGroupIdByLevel(GroupLevelEnum level) {
        LinGroup group = getParticularGroupByLevel(level);
        return group == null ? 0 : group.getId();
    }

    private LinGroup getParticularGroupByLevel(GroupLevelEnum level) {
        if (GroupLevelEnum.USER.equals(level)) {
            // ? 这里为什么匹配到user就直接返回空，这样做的意义是什么？？？？？
            return null;
        } else {
            QueryWrapper<LinGroup> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(LinGroup::getLevel, level);
            LinGroup group = baseMapper.selectOne(wrapper);
            return group;
        }
    }
}
