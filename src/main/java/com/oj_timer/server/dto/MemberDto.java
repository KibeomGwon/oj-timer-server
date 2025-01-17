package com.oj_timer.server.dto;

import com.oj_timer.server.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private String email;
    private String password;
    private String phone;

    public Member toMember() {
        return Member.create(email, password, phone);
    }

    public static MemberDto toDto(Member member) {
        return MemberDto.builder().
                email(member.getEmail()).
                password(member.getPassword()).
                phone(member.getPhone()).
                build();
    }
}
