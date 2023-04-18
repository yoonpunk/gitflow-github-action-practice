package com.practice.git.service;

import com.practice.git.domain.Member;
import com.practice.git.repository.MemberRepository;
import com.practice.git.service.command.CreateMemberCommand;
import com.practice.git.service.exception.ResourceAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void createMember(CreateMemberCommand command) {

        Optional<Member> byPhone = memberRepository.findByPhone(command.getPhone());
        if (byPhone.isPresent()) {
            throw new ResourceAlreadyExistException(ResourceAlreadyExistException.MEMBER_ALREADY_EXIST);
        }

        Member member = Member.createMember(command.getName(), command.getPhone(), command.getCreatedAt());
        member = this.memberRepository.save(member);
    }
}
