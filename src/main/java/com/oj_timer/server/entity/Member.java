package com.oj_timer.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Member extends EntityDate {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(length = 11)
    private String phone;
    @OneToMany(mappedBy = "member")
    private List<Submission> submissions = new ArrayList<>();

    private Member(String email, String password, String phone) {
        this.email = email;
        this.password = password;
        this.phone = phone;
    }


    // === 생성 메소드 === //
    public static Member create(String email, String password, String phone) {
        return new Member(email, password, phone);
    }


}
