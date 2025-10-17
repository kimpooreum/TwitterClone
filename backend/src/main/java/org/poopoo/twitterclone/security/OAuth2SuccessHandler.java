package org.poopoo.twitterclone.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.poopoo.twitterclone.entity.User;
import org.poopoo.twitterclone.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication)
        throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // GitHub에서 제공하는 기본 정보들
        Object emailObj = oAuth2User.getAttributes().get("email");
        Object loginObj = oAuth2User.getAttributes().get("login");

        // email이 null일 수도 있으므로 login 값으로 대체
        String email = (emailObj != null) ? emailObj.toString() : loginObj.toString();
        String nickname = (loginObj != null) ? loginObj.toString() : "unknown";

        // DB에 사용자 없으면 새로 등록
        User user = userRepository.findByEmail(email)
            .orElseGet(() -> {
                User newUser = User.builder()
                    .email(email)
                    .nickname(nickname)
                    .password("") // 소셜 로그인은 비밀번호 없음
                    .role("ROLE_USER")
                    .build();
                return userRepository.save(newUser);
            });

        // JWT 발급
        String token = jwtService.generateToken(new CustomUserDetails(user));

        // JSON 응답
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
    }
}
