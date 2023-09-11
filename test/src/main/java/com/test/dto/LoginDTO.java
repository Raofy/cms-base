package com.test.dto;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Created by Rao on 2023/9/8 10:54
 */
@Data
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message = "{username.not-blank}")
    private String username;
    @NotBlank(message = "{password.new.not-blank}")
    private String password;
}
