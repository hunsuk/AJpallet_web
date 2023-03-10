package com.example.azprojectpallet.domain;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@NoArgsConstructor
@Entity
@Table(name="Account")
@Setter
@Getter
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "auth")
    private String auth;

    @Column(name = "corpName") //회사이름
    private String corpName; //회사이름

    @Column(name = "location") //위치
    private String location;

    @Column(name = "corpNumber") //사업자등록번호
    private String corpNumber; //사업자등록번호

    @Column(name = "managerName") //담당자이름
    private String managerName; //담당자이름

    @Column(name="corpPhoneNumber")
    private String corpPhoneNumber;
    @CreatedDate
    private LocalDateTime createAt; // 생성시간

    @LastModifiedDate
    private LocalDateTime modifiedAt; // 최후로그인시간

    @OneToMany(mappedBy = "rentAccount")
    private List<RentItem> rents;
//
    @OneToMany(mappedBy = "orderAccount")
    private List<Reservation> reservations;



    @Builder
    public User(String email, String phone, String password, String auth, String location, String corpNumber, String corpName, String managerName, LocalDateTime createAt, LocalDateTime modifiedAt, String corpPhoneNumber) {
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.auth = auth;
        this.location = location;
        this.corpNumber = corpNumber;
        this.corpName = corpName;
        this.managerName = managerName;
        this.corpPhoneNumber = corpPhoneNumber;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }

    // 사용자의 권한을 콜렉션 형태로 반환
    // 단, 클래스 자료형은 GrantedAuthority를 구현해야함
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }


    // 사용자의 id를 반환 (unique한 값)
    @Override
    public String getUsername() {
        return email;
    }

    // 사용자의 password를 반환
    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }

}
