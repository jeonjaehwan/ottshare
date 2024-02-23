package com.project.ottshare.service;

import com.project.ottshare.dto.userDto.UserRequest;
import com.project.ottshare.dto.userDto.UserResponse;
import com.project.ottshare.dto.userDto.UserSimpleRequest;

import java.util.Optional;

public interface UserService {

    Long joinUser(UserRequest userRequest);

    UserResponse getUser(Long id);

    void updateUser(UserSimpleRequest userSimpleRequest);

    void deleteUser(Long id);
}
