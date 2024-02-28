package com.project.ottshare.controller.user;

import com.project.ottshare.dto.userDto.*;
import com.project.ottshare.service.user.UserService;
import com.project.ottshare.validation.CustomValidators;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserApiController {

    private final UserService userService;
    private final CustomValidators validators;

    /**
     * 마이페이지
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> myPage(@PathVariable("userId") Long userId) {
        UserResponse userResponse = userService.getUser(userId);

        return ResponseEntity.ok(userResponse);
    }

    /**
     * 회원 수정
     * todo: 테스트 필요
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<?> modify(@PathVariable("userId") Long id,
                                    @RequestBody UserSimpleRequest dto,
                                    BindingResult bindingResult) {
        UserRequest userRequest = new UserRequest(dto.getUsername(), dto.getPassword(), dto.getNickname());

        //유효성 검사
        validators.modifyValidateAll(userRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            // 모든 오류 메시지를 반환
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errorMessages);
        }

        //회원 수정
        userService.updateUser(dto);

        // 변경된 세션 등록
        Authentication authentication = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    /**
     * 회원 탙퇴
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> delete(@PathVariable("userId") Long id) {
        userService.deleteUser(id);

        return new ResponseEntity<>("User Deleted successfully", HttpStatus.OK);
    }

    /**
     * 인증번호 전송
     */
    @PostMapping("/find-username")
    public ResponseEntity<String> findUsername(@RequestBody FindUsernameRequest dto) {
        userService.sendSmsToFindEmail(dto);
        return ResponseEntity.ok("SMS가 성공적으로 전송되었습니다.");
    }

    /**
     * 인증번호 확인
     */
    @PostMapping("/verify-verification-code")
    public ResponseEntity<String> verifyVerificationCode(@RequestBody VerificationCodeRequest request) {
        String enteredVerificationCode = request.getVerificationCode(); // 사용자가 입력한 인증번호

        // TODO: redis사용하면 변경해야 함
        String savedVerificationCode = "";

        if (enteredVerificationCode.equals(savedVerificationCode)) {
            return ResponseEntity.ok("인증번호가 일치합니다.");
        } else {
            return ResponseEntity.badRequest().body("인증번호가 일치하지 않습니다.");
        }
    }

}
