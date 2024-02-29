package com.project.ottshare.service.user;

import com.project.ottshare.dto.userDto.FindUsernameRequest;
import com.project.ottshare.dto.userDto.UserRequest;
import com.project.ottshare.dto.userDto.UserResponse;
import com.project.ottshare.dto.userDto.UserSimpleRequest;

public interface UserService {

    Long joinUser(UserRequest userRequest);

    UserResponse getUser(Long id);

    String getUsername(String name, String phoneNumber);

    String getPassword(String name, String username, String email);

    void updateUser(UserSimpleRequest userSimpleRequest);

    void deleteUser(Long id);

    void sendSmsToFindEmail(FindUsernameRequest findUsernameRequest);

    void verifySms(FindUsernameRequest findUsernameRequest);

}
