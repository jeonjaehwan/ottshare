package com.project.ottshare.service.user;

import com.project.ottshare.config.SmsUtil;
import com.project.ottshare.dto.userDto.FindUsernameRequest;
import com.project.ottshare.dto.userDto.UserRequest;
import com.project.ottshare.dto.userDto.UserResponse;
import com.project.ottshare.dto.userDto.UserSimpleRequest;
import com.project.ottshare.entity.User;
import com.project.ottshare.exception.UserNotFoundException;
import com.project.ottshare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final SmsUtil smsconfig;

    @Override
    @Transactional
    public Long joinUser(UserRequest userRequest) {
        //password 인코딩
        userRequest.setPassword(encoder.encode(userRequest.getPassword()));

        //userRequest -> user
        User user = userRequest.toEntity();

        //user 저장
        userRepository.save(user);

        return user.getId();
    }

    @Override
    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        //user -> userResponse
        UserResponse userResponse = new UserResponse(user);

        return userResponse;
    }

    @Override
    @Transactional
    public void updateUser(UserSimpleRequest userSimpleRequest) {
        User user = userRepository.findById(userSimpleRequest.getId())
                .orElseThrow(() -> new UserNotFoundException(userSimpleRequest.getId()));

        //user 정보 수정
        user.update(encoder.encode(userSimpleRequest.getPassword()), userSimpleRequest.getNickname());
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        //user 삭제
        userRepository.delete(user);
    }

    @Override
    public void sendSmsToFindEmail(FindUsernameRequest findUsernameRequest) {
        String name = findUsernameRequest.getName();
        String phoneNum = findUsernameRequest.getPhoneNumber().replaceAll("-", "");
        User user = userRepository.findByNameAndPhoneNumber(name, phoneNum)
                .orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다."));
        String receiverEmail = user.getEmail();
        String verificationCode = UUID.randomUUID().toString().substring(0, 6); // 무작위 인증 코드 생성
        smsconfig.sendOne(phoneNum, verificationCode);

//        redisUtil.setDataExpire(verificationCode, receiverEmail, 60 * 5L);
    }
}
