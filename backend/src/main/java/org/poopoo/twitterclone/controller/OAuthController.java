package org.poopoo.twitterclone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OAuth2 로그인 성공 후 리다이렉트되는 엔드포인트를 처리하는 컨트롤러
 * GitHub 로그인 성공 시 JWT 토큰은 OAuth2SuccessHandler에서 자동 발급됨
 */
@RestController
@RequestMapping("/api/auth")
public class OAuthController {

    /**
     * OAuth2 로그인 성공 시 호출되는 엔드포인트
     * (SecurityConfig에서 .defaultSuccessUrl("/api/auth/oauth2/success") 로 설정됨)
     */
    @GetMapping("/oauth2/success")
    public String oauth2Success() {
        return "OAuth 로그인 성공! JWT 토큰이 발급되었습니다.";
    }

    /**
     * 테스트용 단순 연결 확인
     */
    @GetMapping("/oauth2/test")
    public String oauth2Test() {
        return "OAuth2 연결 테스트 성공!";
    }
}
