package com.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.test.entity.BaseEntity;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * Created by Rao on 2023/09/01 17:36
 */
@Getter
@Setter
@TableName("lin_group_permission")
public class LinGroupPermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分组id
     */
    private Integer groupId;

    /**
     * 权限id
     */
    private Integer permissionId;
}
