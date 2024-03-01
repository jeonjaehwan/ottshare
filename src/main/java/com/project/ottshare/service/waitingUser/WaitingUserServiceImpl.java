package com.project.ottshare.service.waitingUser;

import com.project.ottshare.entity.WaitingUser;
import com.project.ottshare.enums.OttType;
import com.project.ottshare.exception.OttLeaderNotFoundException;
import com.project.ottshare.exception.OttNonLeaderNotFoundException;
import com.project.ottshare.repository.WaitingUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class WaitingUserServiceImpl implements WaitingUserService{

    private final WaitingUserRepository waitingUserRepository;

    /**
     * 리더가 있는지 확인
     */
    @Override
    public WaitingUser findLeaderByOtt(OttType ott) {
        WaitingUser waitingUser = waitingUserRepository.findLeadersByOtt(ott)
                .orElseThrow(() -> new OttLeaderNotFoundException(ott));

        return waitingUser;
    }

    /**
     * 리더가 아닌 사람이 모두 있는지 확인
     */
    @Override
    public List<WaitingUser> findNonLeaderByOtt(OttType ott) {
        int nonLeaderCount = getNonLeaderCountByOtt(ott);
        Pageable pageRequest = PageRequest.of(0, nonLeaderCount);

        List<WaitingUser> waitingUsers = waitingUserRepository.findNonLeadersByOtt(ott, pageRequest)
                .orElseThrow(() -> new OttNonLeaderNotFoundException(ott));

        return waitingUsers;
    }

    private int getNonLeaderCountByOtt(OttType ott) {
        switch (ott) {
            case NETFLIX:
                return 2;
            case DISNEY_PLUS:
                return 3;
            case TVING:
                return 3;
            default:
                throw new IllegalArgumentException("Unsupported OttType: " + ott);
        }
    }
}
