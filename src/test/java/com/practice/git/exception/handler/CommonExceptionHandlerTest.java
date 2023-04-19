package com.practice.git.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.git.controller.MemberController;
import com.practice.git.controller.Request.CreateMemberRequest;
import com.practice.git.service.MemberService;
import com.practice.git.service.command.CreateMemberCommand;
import com.practice.git.service.exception.ResourceAlreadyExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(MemberController.class)
class CommonExceptionHandlerTest {

    @MockBean
    MemberService memberService;

    @Autowired
    MockMvc sut;

    @Test
    void testForResourceAlreadyExistException() throws Exception {

        // arrange
        CreateMemberRequest request = CreateMemberRequest.builder()
                .name("YYS")
                .phone("01098765432")
                .build();

        LocalDateTime createdAt = LocalDateTime.of(2023, 04, 19, 01, 30, 00, 000);

        doThrow(new ResourceAlreadyExistException(ResourceAlreadyExistException.MEMBER_ALREADY_EXIST))
                .when(memberService).createMember(any(CreateMemberCommand.class));

        // act
        sut.perform(MockMvcRequestBuilders.post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(request)))
                // assert
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(ResourceAlreadyExistException.MEMBER_ALREADY_EXIST));

        // assert
        verify(memberService, times(1)).createMember(any(CreateMemberCommand.class));
    }

    private String toJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}