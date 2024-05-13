package com.project.ottshare.service.sharingUser;

import com.project.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import com.project.ottshare.dto.sharingUserDto.SharingUserResponse;
import com.project.ottshare.dto.waitingUserDto.WaitingUserResponse;
import com.project.ottshare.entity.OttShareRoom;
import com.project.ottshare.entity.SharingUser;
import com.project.ottshare.exception.OttSharingRoomNotFoundException;
import com.project.ottshare.repository.OttShareRoomRepository;
import com.project.ottshare.repository.SharingUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
@Slf4j
public class SharingUserServiceImpl implements SharingUserService{

    private final OttShareRoomRepository ottShareRoomRepository;
    private final SharingUserRepository sharingUserRepository;


    @Override
    @Transactional
    public List<SharingUser> prepareSharingUsers(List<WaitingUserResponse> responses) {
        List<SharingUser> sharingUser = new ArrayList<>();
        for (WaitingUserResponse member : responses) {
            SharingUser entity = member.toEntity();
            sharingUser.add(entity);
            entity.getUser().checkShareRoom();
        }
        return sharingUser;
    }

    @Override
    @Transactional
    public void associateRoomWithSharingUsers(List<SharingUser> sharingUsers, OttShareRoomResponse room) {
        for (SharingUser sharingUser : sharingUsers) {
            log.info("room={}", room.getId());
            OttShareRoom entity = room.toEntity();
            log.info("room2={}", entity.getId());
            sharingUser.addRoom(entity);
        }
    }

    @Override
    public SharingUserResponse getSharingUser(Long userId) {
        SharingUser sharingUser = sharingUserRepository.findByUserId(userId)
                .orElseThrow(() -> new OttSharingRoomNotFoundException(userId));

        SharingUserResponse sharingUserResponse = new SharingUserResponse(sharingUser);

        return sharingUserResponse;
    }


}