package com.test.service;

import com.test.entity.LinGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Rao
 * @since 2023/09/01 17:31
 */
public interface ILinGroupService extends IService<LinGroup> {

    boolean checkIsRootByUserId(Long id);

    List<Long> getGroupIdsById(Long id);
}
