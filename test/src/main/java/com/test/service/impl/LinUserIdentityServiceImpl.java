package com.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rfy.util.EncrypUtil;
import com.test.common.constant.IdentityConstant;
import com.test.entity.LinUserIdentity;
import com.test.mapper.LinUserIdentityMapper;
import com.test.service.ILinUserIdentityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Rao
 * @since 2023/09/01 17:28
 */
@Service
public class LinUserIdentityServiceImpl extends ServiceImpl<LinUserIdentityMapper, LinUserIdentity> implements ILinUserIdentityService {

    @Override
    public boolean verifyPassword(Long userId, String username, String password) {
        QueryWrapper<LinUserIdentity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LinUserIdentity::getUserId, userId)
                .eq(LinUserIdentity::getIdentityType, IdentityConstant.USERNAME_PASSWORD_IDENTITY)
                .eq(LinUserIdentity::getIdentifier, username);
        LinUserIdentity one = baseMapper.selectOne(queryWrapper);
        boolean verify = EncrypUtil.verify(one.getCredential(), password);
        return verify;
    }
}
