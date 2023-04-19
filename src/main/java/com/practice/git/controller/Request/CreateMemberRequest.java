package com.practice.git.controller.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class CreateMemberRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    @Schema(description = "회원 가입자 이름", defaultValue = "홍길동")
    private String name;

    @NotBlank(message = "휴대전화번호를 반드시 숫자만 입력해주세요.")
    @Pattern(regexp = "01(0|1|6|7|8|9)([0-9]{3,4})([0-9]{4})",
            message = "휴대전화패턴이 맞지 않습니다. 올바른 번호를 숫자만 입력해주세요.")
    @Schema(description = "회원 가입자 휴대전화번호", defaultValue = "01012345678")
    private String phone;
}
