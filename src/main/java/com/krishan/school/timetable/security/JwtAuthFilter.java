package com.krishan.school.timetable.security;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.krishan.school.timetable.entity.User;
import com.krishan.school.timetable.repository.UserRepository;
import com.krishan.school.timetable.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Skip JWT check for login/register
        String path = request.getRequestURI();
        if (path.startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String token = null, username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            if (jwtService.isTokenValid(token)) {
                username = jwtService.extractUsername(token);
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userRepository.findByUsername(username).orElse(null);
            if (user != null) {
                var authorities = java.util.List.of(
                        new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
                );

                var authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
