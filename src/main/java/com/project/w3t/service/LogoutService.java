package com.project.w3t.service;

import com.project.w3t.security.auth.SetupDataLoader;
import com.project.w3t.security.config.JwtAuthenticationFilter;
import com.project.w3t.security.config.JwtService;
import com.project.w3t.security.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        final String authHeader = request.getHeader(JwtAuthenticationFilter.AUTHORIZATION);
        final String jwt;
        if (authHeader == null || !authHeader.startsWith(JwtAuthenticationFilter.BEARER_)) {
            return;
        }
        jwt = authHeader.substring(JwtAuthenticationFilter.BEGIN_INDEX);
        var storedToken = tokenRepository.findByToken(jwt).orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
