package com.test.mapper;

import com.test.entity.LinGroup;
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
 * @since 2023/09/01 17:31
 */
@Mapper
public interface LinGroupMapper extends BaseMapper<LinGroup> {

    List<Long> getGroupIdsById(@Param("id") Long id);
}
