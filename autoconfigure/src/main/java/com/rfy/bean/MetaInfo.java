package com.rfy.bean;

import com.rfy.enumeration.UserLevel;

/**
 * 权限校验元数据
 *
 * Created by Rao on 2023/8/31 14:31
 */
public class MetaInfo {

    private String module;
    private String permission;
    private String identity;
    private UserLevel userLevel;

    public MetaInfo() {
    }

    public MetaInfo(String module, String permission, String identity, UserLevel userLevel) {
        this.module = module;
        this.permission = permission;
        this.identity = identity;
        this.userLevel = userLevel;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public UserLevel getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(UserLevel userLevel) {
        this.userLevel = userLevel;
    }
}
