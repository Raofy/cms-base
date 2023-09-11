package com.test.service;

import com.test.entity.LinUserIdentity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Rao
 * @since 2023/09/01 17:28
 */
public interface ILinUserIdentityService extends IService<LinUserIdentity> {

    boolean verifyPassword(Long userId, String username, String password);
}
