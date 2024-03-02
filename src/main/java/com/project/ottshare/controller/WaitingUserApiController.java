package com.project.ottshare.controller;

import com.project.ottshare.dto.waitingUserDto.WaitingUserRequest;
import com.project.ottshare.dto.waitingUserDto.WaitingUserResponse;
import com.project.ottshare.entity.WaitingUser;
import com.project.ottshare.enums.OttType;
import com.project.ottshare.service.waitingUser.WaitingUserService;
import com.project.ottshare.validation.ValidationSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/waitingUser")
public class WaitingUserApiController {

    private final WaitingUserService waitingUserService;

    /**
     * user 저장 -> 인원이 다 모이면 방 생성
     */
    @PostMapping("/matchings")
    public ResponseEntity<List<WaitingUserResponse>> matchUsersToOtt(@Validated(ValidationSequence.class) @RequestBody WaitingUserRequest dto) {
        //waitingUser에 user저장
        waitingUserService.saveUser(dto);

        //해당 ott에 리더가 있는지 확인, 없으면 오류
        WaitingUserResponse leaderByOtt = waitingUserService.findLeaderByOtt(dto.getOtt());
        //해당 ott에 리더가 아닌 user가 모두 모였는지 확인, 없으면 오류
        List<WaitingUserResponse> nonLeaderByOtt = waitingUserService.findNonLeaderByOtt(dto.getOtt());

        List<WaitingUserResponse> waitingUserResponses = new ArrayList<>();

        //waitingUserResponses에 리더가 아닌 사람 추가
        for (WaitingUserResponse userResponse : nonLeaderByOtt) {
            waitingUserResponses.add(userResponse);
        }
        //waitingUserResponses에 리더 추가
        waitingUserResponses.add(leaderByOtt);

        //모인 인원 waitingUser에서 삭제
        waitingUserService.deleteUsers(waitingUserResponses);


        return ResponseEntity.ok(waitingUserResponses);
    }

    /**
     * user 삭제
     */
    @DeleteMapping("/matchings/{matchingId}")
    public ResponseEntity<String> deleteWaitingUser(@PathVariable("matchingId") Long matchingId) {
        //user 삭제
        waitingUserService.deleteUser(matchingId);

        return ResponseEntity.ok("User deleted successfully");
    }

}
