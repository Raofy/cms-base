package com.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 * <p>
 * Created by Rao on 2023/09/01 17:17
 */
@Getter
@Setter
@TableName("lin_user")
public class LinUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名，唯一
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 头像url
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;
}
