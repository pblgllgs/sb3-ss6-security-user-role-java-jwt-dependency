package com.pblgllgs.sb3ss6securityuserrolejjwt.filter;
/*
 *
 * @author pblgl
 * Created on 26-03-2024
 *
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pblgllgs.sb3ss6securityuserrolejjwt.model.ErrorResponse;
import com.pblgllgs.sb3ss6securityuserrolejjwt.repository.UserRepository;
import com.pblgllgs.sb3ss6securityuserrolejjwt.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Value("${secret-key}")
    private String secretKey;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String accessToken = authorizationHeader.substring(7);
                Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(accessToken);
                String username = decodedJWT.getSubject();
                userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("INVALID_TOKEN"));

                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

                Authentication authentication
                        = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        authorities
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } catch (RuntimeException ex) {
                ErrorResponse errorResponse = ErrorResponse.builder()
                        .httpStatus(HttpStatus.FORBIDDEN)
                        .errorMessage(ex.getMessage())
                        .build();
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(errorResponse.getStatusCodeValue());
                new ObjectMapper().writeValue(response.getOutputStream(),errorResponse);
                log.error(ex.getMessage());

            }
        }
        filterChain.doFilter(request, response);
    }
}
