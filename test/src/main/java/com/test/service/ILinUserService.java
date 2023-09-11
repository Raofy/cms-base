package com.test.service;

import com.test.entity.LinPermission;
import com.test.entity.LinUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Rao
 * @since 2023/09/01 17:17
 */
public interface ILinUserService extends IService<LinUser> {

    List<LinPermission> getUserPermission(LinUser user);

    LinUser getByUsername(String username);
}
