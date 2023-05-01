package com.practice.git.service;

import com.practice.git.domain.Member;
import com.practice.git.repository.MemberRepository;
import com.practice.git.service.command.CreateMemberCommand;
import com.practice.git.service.dto.SearchedMemberDto;
import com.practice.git.service.exception.ResourceAlreadyExistException;
import com.practice.git.service.exception.ResourceNotExistException;
import com.practice.git.service.query.SearchMemberQuery;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.OpenApiResourceNotFoundException;
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

    public SearchedMemberDto searchMember(SearchMemberQuery query) {

        Optional<Member> byPhone = memberRepository.findByPhone(query.getPhone());

        if (byPhone.isEmpty()) {
            throw new ResourceNotExistException(ResourceNotExistException.MEMBER_NOT_EXIST);
        }

        Member member = byPhone.get();

        return SearchedMemberDto.builder()
                .name(member.getName())
                .phone(member.getPhone())
                .build();
    }
}
