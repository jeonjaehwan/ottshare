package com.project.ottshare.controller.user;

import com.project.ottshare.dto.userDto.*;
import com.project.ottshare.service.user.UserService;
import com.project.ottshare.validation.CustomValidators;
import com.project.ottshare.validation.ValidationSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final CustomValidators validators;

    /**
     * 회원가입
     */
    @PostMapping("/join")
    public String joinProc(@Validated(ValidationSequence.class) @ModelAttribute("user") UserRequest dto,
                           BindingResult bindingResult,
                           Model model) {
        //중복 검사
        validators.joinValidateAll(dto, bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult.getFieldError());
            model.addAttribute("user", dto);

            return "user/join";
        }

        //회원 저장
        userService.joinUser(dto);
        log.info("회원가입 완료 username={}", dto.getUsername());

        return "redirect:/users/login";
    }

    /**
     * 회원정보 수정
     */
    @GetMapping("/{userId}/modification")
    public String modify(@PathVariable("userId") Long userId,
                         Model model) {
        UserResponse user = userService.getUser(userId);
        model.addAttribute("user", user);

        return "user/modify";
    }
}
