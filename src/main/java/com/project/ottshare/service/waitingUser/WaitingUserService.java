package com.project.ottshare.service.waitingUser;

import com.project.ottshare.dto.waitingUserDto.WaitingUserRequest;
import com.project.ottshare.dto.waitingUserDto.WaitingUserResponse;
import com.project.ottshare.entity.WaitingUser;
import com.project.ottshare.enums.OttType;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface WaitingUserService {

    void saveUser(WaitingUserRequest waitingUserRequest);

    void deleteUsers(List<WaitingUserResponse> waitingUsers);

    void deleteUser(Long id);

    WaitingUserResponse findLeaderByOtt(OttType ott);

    List<WaitingUserResponse> findNonLeaderByOtt(OttType ott);
}
