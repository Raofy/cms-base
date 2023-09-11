package com.test.common.util;

import com.test.entity.LinUser;

/**
 * 线程安全的在线用户管理
 *
 * Created by Rao on 2023/9/7 11:37
 */
public class LocalUser {

    private static ThreadLocal<LinUser> local = new ThreadLocal<>();

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public static LinUser getLocalUser() {
        return local.get();
    }

    /**
     * 设置当前登录用户
     *
     * @param user
     */
    public static void setLocalUser(LinUser user) {
        local.set(user);
    }

    public static <T> T getLocalUser(Class<T> clazz) {
        return (T) local.get();
    }

    /**
     * 清理当前登录用户
     */
    public static void cleanLocalUser() {
        local.remove();
    }
}
