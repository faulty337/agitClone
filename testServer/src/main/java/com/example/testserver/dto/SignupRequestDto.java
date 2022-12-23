package com.example.testserver.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {
    private String username;
    private String password;
    private String checkPassword;
    private String nickname;
}
