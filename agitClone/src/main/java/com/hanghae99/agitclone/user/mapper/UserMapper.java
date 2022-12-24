package com.hanghae99.agitclone.user.mapper;

import com.hanghae99.agitclone.common.exception.CustomException;
import com.hanghae99.agitclone.user.dto.SignupRequestDto;
import com.hanghae99.agitclone.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.hanghae99.agitclone.common.exception.ErrorCode.PASSWORD_MISMATCH;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public Users toUser(SignupRequestDto signupRequestDto) {
        if (!Objects.equals(signupRequestDto.getPassword(), signupRequestDto.getCheckPassword())){
            throw new CustomException(PASSWORD_MISMATCH);
        }
        return Users.builder()
                .username(signupRequestDto.getUsername())
                .nickname(signupRequestDto.getNickname())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .build();
    }
}
