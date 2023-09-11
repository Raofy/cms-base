package com.test.mapper;

import com.test.entity.LinPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Rao
 * @since 2023/09/01 17:35
 */
@Mapper
public interface LinPermissionMapper extends BaseMapper<LinPermission> {

    List<LinPermission> getPermissionByGroupIds(@Param("groupIds") List<Long> groupIds);
}
