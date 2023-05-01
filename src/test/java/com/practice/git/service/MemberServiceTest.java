package com.practice.git.service;

import com.practice.git.domain.Member;
import com.practice.git.repository.MemberRepository;
import com.practice.git.service.command.CreateMemberCommand;
import com.practice.git.service.dto.SearchedMemberDto;
import com.practice.git.service.exception.ResourceAlreadyExistException;
import com.practice.git.service.exception.ResourceNotExistException;
import com.practice.git.service.query.SearchMemberQuery;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.yml")
class MemberServiceTest {

    @Autowired
    MemberService sut;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void testForCreateMemberWithNewMember() {

        // arrange
        CreateMemberCommand command = CreateMemberCommand.builder()
                .name("YYS")
                .phone("01098765432")
                .createdAt(LocalDateTime.of(2023, 04, 19, 01, 30, 00, 000))
                .build();

        // act
        sut.createMember(command);

        // assert
        assertCreatedMember(command);
    }

    @Test
    void testForCreateMemberWithExistMember() {

        // arrange
        Member member = Member.createMember("YYS", "01098765432",
                LocalDateTime.of(2023, 04, 19, 01, 30, 00, 000));

        member = memberRepository.save(member);

        CreateMemberCommand command = CreateMemberCommand.builder()
                .name(member.getName())
                .phone(member.getPhone())
                .createdAt(member.getCreatedAt())
                .build();

        // act & assert
        assertThrows(ResourceAlreadyExistException.class, () -> sut.createMember(command));
    }

    @Test
    void testForSearchMemberWithExistMember() {

        // arrange
        Member expectedMember = Member.createMember("yys", "01012345678",
                LocalDateTime.of(2023, 5, 1, 12, 0, 0, 0));

        expectedMember = memberRepository.save(expectedMember);

        SearchMemberQuery query = SearchMemberQuery.builder()
                .phone(expectedMember.getPhone())
                .build();

        // act
        SearchedMemberDto actualSearchedMemberDto = sut.searchMember(query);

        // assert
        assertSearchMember(actualSearchedMemberDto, expectedMember);
    }

    @Test
    void testForSearchMemberWithNotExistMember() {

        // arrange
        SearchMemberQuery query = SearchMemberQuery.builder()
                .phone("33333333333")
                .build();

        // act & assert
        assertThrows(ResourceNotExistException.class, () -> sut.searchMember(query));
    }

    private void assertSearchMember(SearchedMemberDto actualSearchedMemberDto, Member expected) {

        Assertions.assertThat(actualSearchedMemberDto.getName()).isEqualTo(expected.getName());
        Assertions.assertThat(actualSearchedMemberDto.getPhone()).isEqualTo(expected.getPhone());

    }

    // assert for the created member.
    private void assertCreatedMember(CreateMemberCommand command) {

        Optional<Member> optionalMember = memberRepository.findByPhone(command.getPhone());
        Assertions.assertThat(optionalMember).isPresent();

        Member member = optionalMember.get();
        Assertions.assertThat(member.getName()).isEqualTo(command.getName());
        Assertions.assertThat(member.getPhone()).isEqualTo(command.getPhone());
        Assertions.assertThat(member.getCreatedAt()).isEqualTo(command.getCreatedAt());
        Assertions.assertThat(member.getModifiedAt()).isEqualTo(command.getCreatedAt());
    }
}