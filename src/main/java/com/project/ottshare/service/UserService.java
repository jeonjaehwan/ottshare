package com.project.ottshare.service;

import com.project.ottshare.dto.UserRequest;
import com.project.ottshare.repository.UserRepository;

public interface UserService {

    Long joinUser(UserRequest userRequest);


}
