package com.hanghae99.agitclone.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae99.agitclone.common.ResponseMessage;
import com.hanghae99.agitclone.security.UserDetailsServiceImpl;
import com.hanghae99.agitclone.security.jwt.JwtAuthFilter;
import com.hanghae99.agitclone.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.hanghae99.agitclone.common.exception.ErrorCode.FORBIDDEN_ERROR;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원설정 가능하게 함
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // csrf 중지
        http.csrf().disable();
        // 세션정책 중지
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // api 허용정책 설정
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, new String[]{"/user/signup","/user/login", "/user/idcheck"}).permitAll()
                .antMatchers(HttpMethod.GET, "/user/idcheck").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
//                .antMatchers(HttpMethod.GET, new String[]{"/api/members/kakao","/api/members/search"}).permitAll()
//                .antMatchers(HttpMethod.GET, new String[]{"/api/posts","/api/posts/{id}"}).permitAll()
                .anyRequest().authenticated()
                // JWT 인증/인가를 사용하기 위해 JwtAuthFilter 적용
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling()
                .authenticationEntryPoint(unauthorizedEntryPoint)
                .and().headers().frameOptions().disable()
                // 이 설정을 해주지 않으면 밑의 corsConfigurationSource가 적용되지 않습니다!
                .and().cors();

        return http.build();
    }
    //403 Forbidden 예외처리
    private final AuthenticationEntryPoint unauthorizedEntryPoint =
            (request, response, authException) -> {
                response.setStatus(FORBIDDEN_ERROR.getStatusCode());
                String json = new ObjectMapper().writeValueAsString(new ResponseMessage<>(FORBIDDEN_ERROR, FORBIDDEN_ERROR));
                response.setContentType("application/json; charset=utf8");
                response.getWriter().write(json);
            };

    /**
     * 이 설정.
     * cors 개념 참고 - https://inpa.ti을 해주면, 우리가 설정한대로 CorsFilter가 Security의 filter에 추가되어
     *      * 예비 요청에 대한 처리를 해주게 됩니다story.com/entry/WEB-%F0%9F%93%9A-CORS-%F0%9F%92%AF-%EC%A0%95%EB%A6%AC-%ED%95%B4%EA%B2%B0-%EB%B0%A9%EB%B2%95-%F0%9F%91%8F
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:3000");
        config.addExposedHeader(JwtUtil.AUTHORIZATION_HEADER);
        config.addAllowedMethod("*");

        // 본 요청에 허용할 HTTP header(예비 요청에 대한 응답 헤더에 추가됨)
        // Access-Control-Allow-Headers
        config.addAllowedHeader("*");

        // 기본적으로 브라우저에서 인증 관련 정보들을 요청 헤더에 담지 않음
        // 이 설정을 통해서 브라우저에서 인증 관련 정보들을 요청에 담을 수 있도록 해줍니다.
        // Access-Control-Allow-Credentials
        config.setAllowCredentials(true);
        config.validateAllowCredentials();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
