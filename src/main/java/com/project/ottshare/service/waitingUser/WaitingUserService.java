package com.project.ottshare.service.waitingUser;

import com.project.ottshare.entity.WaitingUser;
import com.project.ottshare.enums.OttType;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface WaitingUserService {

    WaitingUser findLeaderByOtt(OttType ott);

    List<WaitingUser> findNonLeaderByOtt(OttType ott);
}
