package com.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * Created by Rao on 2023/09/01 17:35
 */
@Getter
@Setter
@TableName("lin_permission")
public class LinPermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 权限名称，例如：访问首页
     */
    private String name;

    /**
     * 权限所属模块，例如：人员管理
     */
    private String module;

    /**
     * 0：关闭 1：开启
     */
    private Boolean mount;
}
