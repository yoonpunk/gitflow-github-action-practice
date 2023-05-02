package com.practice.git.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.git.controller.Request.CreateMemberRequest;
import com.practice.git.service.MemberService;
import com.practice.git.service.command.CreateMemberCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(MemberController.class)
@TestPropertySource(locations= "classpath:application.yml")
class MemberControllerTest {

    @Autowired
    MockMvc sut;

    @MockBean
    MemberService memberService;

    @Test
    void testForCreateMemberWhenNewMember() throws Exception {

        // arrange
        CreateMemberRequest request = CreateMemberRequest.builder()
                .name("YYS")
                .phone("01098765432")
                .build();

        LocalDateTime createdAt = LocalDateTime.of(2023, 04, 19, 01, 30, 00, 000);

        // act
        sut.perform(MockMvcRequestBuilders.post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(request)))
                // assert
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Success"));

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