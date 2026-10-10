package com.radon.application.config;

import com.radon.application.port.out.UserRepository;
import com.radon.application.service.AuthUserDetailsService;
import com.radon.application.service.JwtUtil;
import com.radon.application.service.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AuthUserDetailsService authUserDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, AuthUserDetailsService authUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.authUserDetailsService = authUserDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring("Bearer ".length());

        try{
            String userName = jwtUtil.extractUsername(token);

            if(SecurityContextHolder.getContext().getAuthentication() == null){

                UserDetails userDetails = authUserDetailsService.loadUserByUsername(userName);

                if(jwtUtil.isTokenValid(token,userDetails)){

                    var authentication = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authentication.setDetails(new WebAuthenticationDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            }

        } catch (JwtException | IllegalArgumentException |
                 UsernameNotFoundException ex) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);

    }
}
