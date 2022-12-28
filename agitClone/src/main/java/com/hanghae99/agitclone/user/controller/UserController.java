package com.hanghae99.agitclone.user.controller;

import com.hanghae99.agitclone.common.ResponseMessage;
import com.hanghae99.agitclone.security.UserDetailsImpl;
import com.hanghae99.agitclone.user.dto.IdcheckRequestDto;
import com.hanghae99.agitclone.user.dto.LoginRequestDto;
import com.hanghae99.agitclone.user.dto.SignupRequestDto;
import com.hanghae99.agitclone.user.dto.UserinfoResponseDto;
import com.hanghae99.agitclone.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseMessage<?> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return new ResponseMessage<>("Susses",200,null);
    }

    @PostMapping("/login")
    public ResponseMessage<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return new ResponseMessage<>("Susses",200,null);
    }

    @ApiOperation(value = "username 중복 체크")
    @PostMapping("/idcheck")
    public ResponseMessage<?> idcheck(@RequestBody IdcheckRequestDto idcheckRequestDto){
        userService.idcheck(idcheckRequestDto);
        return new ResponseMessage<>("Susses",200,null);
    }

    //프론트에서 토큰이 살아있음으로 유저 로그인 확인을 하기위한 API
    @GetMapping("")
    public ResponseMessage<?> userinfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        UserinfoResponseDto userinfoResponseDto = userService.userinfo(userDetails.getUser());
        return new ResponseMessage<>("Susses",200,userinfoResponseDto);
    }
}
