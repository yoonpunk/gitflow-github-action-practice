package com.practice.git.controller;

import com.practice.git.controller.Request.CreateMemberRequest;
import com.practice.git.service.MemberService;
import com.practice.git.service.command.CreateMemberCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("members")
    public ResponseEntity<String> createMember(@Valid @RequestBody CreateMemberRequest request) {

        CreateMemberCommand command = CreateMemberCommand.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .createdAt(LocalDateTime.now())
                .build();

        memberService.createMember(command);

        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }
}
