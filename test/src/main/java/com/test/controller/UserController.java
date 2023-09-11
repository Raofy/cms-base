package com.test.controller;

import com.rfy.exception.NotFoundException;
import com.rfy.exception.ParameterException;
import com.rfy.token.DoubleJWT;
import com.rfy.token.Tokens;
import com.test.dto.LoginDTO;
import com.test.entity.LinUser;
import com.test.service.ILinUserIdentityService;
import com.test.service.ILinUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Rao on 2023/9/8 10:51
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    ILinUserService iLinUserService;
    @Autowired
    ILinUserIdentityService iLinUserIdentityService;
    @Autowired
    DoubleJWT doubleJWT;

    @PostMapping("login")
    public Tokens login(@RequestBody @Validated LoginDTO dto) {
        String username = dto.getUsername();
        LinUser user = iLinUserService.getByUsername(username);
        if (user == null) {
            throw new NotFoundException(10021, "user is not found");
        }
        boolean valid = iLinUserIdentityService.verifyPassword(user.getId(), user.getUsername(), dto.getPassword());
        if (!valid) {
            throw new ParameterException(10031, "username or password is fault");
        }
        return doubleJWT.generateToken(user.getId());
    }
}
