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
 * Created by Rao on 2023/09/01 17:28
 */
@Getter
@Setter
@TableName("lin_user_identity")
public class LinUserIdentity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer userId;

    private String identityType;

    private String identifier;

    private String credential;
}
