package com.pblgllgs.sb3ss6securityuserrolejjwt.service;
/*
 *
 * @author pblgl
 * Created on 26-03-2024
 *
 */

import com.pblgllgs.sb3ss6securityuserrolejjwt.model.*;
import com.pblgllgs.sb3ss6securityuserrolejjwt.repository.RoleCustomRepo;
import com.pblgllgs.sb3ss6securityuserrolejjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleCustomRepo roleCustomRepo;
    private final UserService userService;

    public ResponseEntity<?> authenticate(AuthenticationRequest request) {
        try {
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("USER_NOT_FOUND"));
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), request.getPassword()));
            List<Role> roles = null;
            if (user != null) {
                roles = roleCustomRepo.getRole(user);
            }
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Set<Role> setRoles = new HashSet<>();

            roles.forEach( role -> {
                setRoles.add(new Role(role.getName()));
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            user.setRoles(setRoles);
            setRoles.forEach( role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

            var jwt = jwtService.generateToken(user,authorities);
            var refreshToken = jwtService.generateRefreshToken(user);
            return ResponseEntity.ok(
                    AuthenticationResponse.builder()
                            .accessToken(jwt)
                            .refreshToken(refreshToken)
                            .email(user.getEmail()).build());
        }catch(UsernameNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch(BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    public ResponseEntity<?> register(RegisterRequest request) {
        try{
            if (userRepository.existsById(request.getEmail())){
                throw new IllegalArgumentException("USER_ALREADY_EXISTS_WITH_EMAIL: " + request.getEmail());
            }
            userService.saveUser(new User(
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword(),
                    request.getMobileNumber(),
                    new HashSet<>())
            );
            userService.addToUser(request.getEmail(),"ROLE_USER");
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            return ResponseEntity.ok(user);
        }catch(UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
