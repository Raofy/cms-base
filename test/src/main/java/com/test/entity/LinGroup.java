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
 * Created by Rao on 2023/09/01 17:31
 */
@Getter
@Setter
@TableName("lin_group")
public class LinGroup extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分组名称，例如：搬砖者
     */
    private String name;

    /**
     * 分组信息：例如：搬砖的人
     */
    private String info;

    /**
     * 分组级别（root、guest分组只能存在一个）
     */
    private String level;
}
