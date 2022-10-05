package com.motivation.mojaty.global.filter.jwt;

import com.motivation.mojaty.global.provider.jwt.JwtProvider;
import com.motivation.mojaty.global.service.jwt.JwtValidateService;
import com.motivation.mojaty.global.service.user.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtProvider jwtProvider;
    private final JwtValidateService jwtValidateService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.resolveToken(request);
        if (token != null) setAuthentication(token, request);
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String token, HttpServletRequest request) throws ExpiredJwtException {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtValidateService.getEmail(token));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
