package com.project.ottshare.dto.waitingUserDto;

import com.project.ottshare.entity.User;
import com.project.ottshare.entity.WaitingUser;
import com.project.ottshare.enums.OttType;
import com.project.ottshare.validation.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WaitingUserRequest {

    @NotBlank(groups = ValidationGroups.NotBlankGroups.class)
    private User user;

    @NotBlank(message = "OTT 선택은 필수 입니다.", groups = ValidationGroups.NotBlankGroups.class)
    private OttType ott;

    @NotBlank(message = "leader 선택은 필수 입니다.", groups = ValidationGroups.NotBlankGroups.class)
    private boolean isLeader;

    public WaitingUser toEntity() {
        WaitingUser waitingUser = WaitingUser.builder()
                .user(user)
                .ott(ott)
                .isLeader(isLeader)
                .build();

        return waitingUser;
    }
}
