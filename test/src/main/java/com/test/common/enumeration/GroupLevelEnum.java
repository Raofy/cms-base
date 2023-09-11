package com.test.common.enumeration;

/**
 * Created by Rao on 2023/9/7 15:09
 */
public enum GroupLevelEnum {
    ROOT("root"),
    USER("user"),
    GUEST("guest");

    private String value;

    GroupLevelEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
