package com.oj_timer.server.service;

import com.oj_timer.server.dto.domain.MemberDto;
import com.oj_timer.server.entity.Member;
import com.oj_timer.server.exception_handler.exceptions.NotFoundException;
import com.oj_timer.server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberDto save(Member member) {
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            // TODO throw RuntimeError
        }
        Member savedMember = memberRepository.save(member);

        return MemberDto.toDto(savedMember);
    }

    public MemberDto findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new NotFoundException("찾지 못했습니다."));
        return MemberDto.toDto(member);
    }

    public MemberDto login(Member member) {
        return MemberDto.toDto(memberRepository.findByEmail(member.getEmail())
                .filter(m -> m.getPassword().equals(member.getPassword()))
                .orElse(null)
        );
    }
}
