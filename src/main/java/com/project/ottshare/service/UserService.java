package com.project.ottshare.service;

import com.project.ottshare.dto.UserRequest;
import com.project.ottshare.dto.UserResponse;
import com.project.ottshare.repository.UserRepository;

import java.util.Optional;

public interface UserService {

    Long joinUser(UserRequest userRequest);

    Optional<UserResponse> getUser(Long id);
}
