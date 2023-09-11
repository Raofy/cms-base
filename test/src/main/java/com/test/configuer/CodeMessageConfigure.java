package com.test.configuer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * http 请求自定义状态码
 *
 * Created by Rao on 2023/9/7 14:58
 */
@Component
@ConfigurationProperties
@PropertySource(value = "classpath:code-message.properties", encoding = "UTF-8")
public class CodeMessageConfigure {

    private static Map<Integer, String> codeMessage = new HashMap<>();

    public static String getMessage(int code) {
        return codeMessage.get(code);
    }

    public static Map<Integer, String> getCodeMessage() {
        return codeMessage;
    }

    public static void setCodeMessage(Map<Integer, String> codeMessage) {
        CodeMessageConfigure.codeMessage = codeMessage;
    }
}
