package com.project.ottshare.validation;

import com.project.ottshare.dto.UserRequest;
import com.project.ottshare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class CustomValidators{

    private final UsernameValidator usernameValidator;
    private final NicknameValidator nicknameValidator;

    /**
     * 아이디, 닉네임 중복 검증
     * @param userRequest
     * @param bindingResult
     */
    public void validateAll(UserRequest userRequest, BindingResult bindingResult) {
        usernameValidator.doValidate(userRequest, bindingResult);
        nicknameValidator.doValidate(userRequest, bindingResult);
    }

    /**
     * 아이디 중복 검증
     */
    @Component
    @RequiredArgsConstructor
    public static class UsernameValidator extends AbstractValidator<UserRequest> {

        private final UserRepository userRepository;
        @Override
        protected void doValidate(UserRequest dto, Errors errors) {
            if (userRepository.existsByUsername(dto.getUsername())) {
                errors.rejectValue("username", "아이디 중복 오류", "이미 사용중인 아이디 입니다.");
            }
        }
    }

    /**
     * 닉네임 중복 검증
     */
    @Component
    @RequiredArgsConstructor
    public static class NicknameValidator extends AbstractValidator<UserRequest> {

        private final UserRepository userRepository;

        @Override
        protected void doValidate(UserRequest dto, Errors errors) {
            if (userRepository.existsByNickname(dto.getNickname())) {
                errors.rejectValue("nickname", "닉네임 중복 오류", "이미 사용중인 닉네임 입니다.");
            }
        }
    }


}
