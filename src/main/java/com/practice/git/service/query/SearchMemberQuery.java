package com.practice.git.service.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchMemberQuery {

    private String phone;
}
