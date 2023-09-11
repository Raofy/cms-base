package com.test.service;

import com.test.entity.LinPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Rao
 * @since 2023/09/01 17:35
 */
public interface ILinPermissionService extends IService<LinPermission> {

    List<LinPermission> getPermissionByGroupIds(List<Long> groupIds);
}
