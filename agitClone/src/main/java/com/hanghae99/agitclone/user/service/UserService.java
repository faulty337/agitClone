package com.hanghae99.agitclone.user.service;

import com.hanghae99.agitclone.common.exception.CustomException;
import com.hanghae99.agitclone.security.jwt.JwtUtil;
import com.hanghae99.agitclone.user.dto.IdcheckRequestDto;
import com.hanghae99.agitclone.user.dto.LoginRequestDto;
import com.hanghae99.agitclone.user.dto.SignupRequestDto;
import com.hanghae99.agitclone.user.dto.UserinfoResponseDto;
import com.hanghae99.agitclone.user.entity.Users;
import com.hanghae99.agitclone.user.mapper.UserMapper;
import com.hanghae99.agitclone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.hanghae99.agitclone.common.exception.ErrorCode.DUPLICATE_USERNAME;
import static com.hanghae99.agitclone.common.exception.ErrorCode.INCORRECT_PASSWORD;
import static com.hanghae99.agitclone.common.exception.ErrorCode.USERNAME_NOT_FOUND;
import static com.hanghae99.agitclone.common.exception.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        Users user = userMapper.toUser(signupRequestDto);

        Optional<Users> found = userRepository.findByUsername(user.getUsername());
        if (found.isPresent()) {
            throw new CustomException(DUPLICATE_USERNAME);
        }

        userRepository.save(user);
    }

    @Transactional
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        Users user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(
                () -> new CustomException(USERNAME_NOT_FOUND)
        );

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new CustomException(INCORRECT_PASSWORD);
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
    }

    @Transactional
    public void idcheck(IdcheckRequestDto idcheckRequestDto) {
        Optional<Users> found = userRepository.findByUsername(idcheckRequestDto.getUsername());
        if (found.isPresent()) {
            throw new CustomException(DUPLICATE_USERNAME);
        }
    }

    @Transactional(readOnly = true)
    public UserinfoResponseDto userinfo(Users user) {
        Users users = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new CustomException(USER_NOT_FOUND)
        );
        return new UserinfoResponseDto(users.getUsername(), users.getNickname());
    }
}
