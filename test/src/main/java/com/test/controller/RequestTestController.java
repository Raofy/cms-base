package com.test.controller;

import com.rfy.annotation.AdminRequire;
import com.rfy.annotation.PermissionMeta;
import com.rfy.annotation.PermissionModule;
import com.rfy.response.Created;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Rao on 2023/8/28 15:47
 */
@RestController
@RequestMapping(path = "request")
@PermissionModule(value = "测试")
public class RequestTestController {

    @GetMapping("create")
    @AdminRequire
    @PermissionMeta(value = "插入操作")
    public Created insert() {
        return new Created();
    }

}
