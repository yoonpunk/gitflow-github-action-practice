package com.practice.git.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchedMemberDto {

    private String name;
    private String phone;

}
