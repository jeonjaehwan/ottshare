package com.project.ottshare.controller;

import com.project.ottshare.dto.userDto.UserRequest;
import com.project.ottshare.dto.userDto.UserSimpleRequest;
import com.project.ottshare.security.auth.CustomUserDetails;
import com.project.ottshare.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserApiController {

    private final UserService userService;

    /**
     * 회원 수정
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<String> modify(@PathVariable("userId") Long id,
                                         @RequestBody UserSimpleRequest dto) {
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
}
