package com.project.ottshare.dto.userDto;

import com.project.ottshare.validation.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import static com.project.ottshare.validation.ValidationGroups.*;

@Getter
public class LoginUserRequest {

    @NotBlank(groups = NotBlankGroups.class, message = "아이디 입력은 필수입니다.")
    private String username;

    @NotBlank(groups = NotBlankGroups.class, message = "비밀번호 입력은 필수입니다.")
    private String password;

}
