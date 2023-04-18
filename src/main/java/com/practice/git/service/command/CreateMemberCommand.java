package com.practice.git.service.command;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreateMemberCommand {

    private String name;
    private String phone;
    private LocalDateTime createdAt;

}
