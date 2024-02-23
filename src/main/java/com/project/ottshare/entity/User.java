package com.project.ottshare.entity;

import com.project.ottshare.dto.userDto.UserRequest;
import com.project.ottshare.dto.userDto.UserSimpleRequest;
import com.project.ottshare.enums.BankType;
import com.project.ottshare.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inquiry> inquiries = new ArrayList<>();

    //중복x
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    //중복x
    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "bank", nullable = false)
    @Enumerated(EnumType.STRING)
    private BankType bank;

    @Column(name = "account", nullable = false)
    private String account;

    @Column(name = "account_holder", nullable = false)
    private String accountHolder;

    @Column(name = "Role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "is_share_room", nullable = false, columnDefinition = "boolean default false")
    private boolean isShareRoom;


    //비즈니스 로직

    /**
     * user정보 수정
     * @param password
     * @param nickname
     */
    public void update(String password, String nickname) {
        this.password = password;
        this.nickname = nickname;
    }
}
