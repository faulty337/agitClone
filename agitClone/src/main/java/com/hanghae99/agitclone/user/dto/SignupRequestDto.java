package com.hanghae99.agitclone.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class SignupRequestDto {
    @NotBlank
    @Pattern(regexp="^(?=.*[a-z0-9])[a-z0-9]{4,12}$", message = "소문자 또는 숫자 4~12자리 입니다.")
    private String username;
    @NotBlank
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,}", message = "영문, 숫자, 특수문자 최소 하나 이상 포함 8자리 이상입니다.")
    private String password;
    @NotBlank
    @Pattern(regexp="^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,10}$", message = "소문자 또는 숫자 또는 한글 2~10자리 입니다.")
    private String nickname;
}
