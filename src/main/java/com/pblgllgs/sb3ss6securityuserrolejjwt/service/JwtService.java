package com.pblgllgs.sb3ss6securityuserrolejjwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pblgllgs.sb3ss6securityuserrolejjwt.model.User;
import com.pblgllgs.sb3ss6securityuserrolejjwt.repository.RoleRepository;
import com.pblgllgs.sb3ss6securityuserrolejjwt.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/*
 *
 * @author pblgl
 * Created on 26-03-2024
 *
 */
@Service
public class JwtService {

    @Value("${secret-key}")
    private String secretKey;

    public String generateToken(User user, Collection<SimpleGrantedAuthority> authorities) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());

        return JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 50 * 60 * 1000))
                .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).toList())
                .withSubject(user.getEmail())
                .sign(algorithm);
    }

    public String generateRefreshToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        return JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 70 * 60 * 1000))
                .withSubject(user.getEmail())
                .sign(algorithm);
    }

}
