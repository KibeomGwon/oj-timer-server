package com.oj_timer.server.dummy.entity;

import com.oj_timer.server.entity.Member;
import com.oj_timer.server.repository.dummy.MemberDummyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@SpringBootTest
public class CreateMemberDummy {

    @Autowired
    MemberDummyRepository repository;

//    @Test
    @DisplayName("멤버 더미 Insert")
    void createDummy() {
        // given
        List<Member> members = getMembers();
        IntStream.range(0, members.size()).forEach(idx -> {
            Member member = members.get(idx);
            member.setId((long) idx);
        });
        // when
        repository.bulkInsert(members);
        // then
    }

    List<Member> getMembers() {
        List<Member> members = new ArrayList<>();

        int password = new Random().nextInt(10000);

        for (int i = 1; i <= 100; i++) {
            Member member = Member.create("email" + i + "@gmail.com", String.valueOf(password) + i, "");
            members.add(member);
        }

        return members;
    }
}
