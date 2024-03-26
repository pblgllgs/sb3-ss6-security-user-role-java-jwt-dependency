package com.pblgllgs.sb3ss6securityuserrolejjwt.controller;

import com.pblgllgs.sb3ss6securityuserrolejjwt.model.AuthenticationRequest;
import com.pblgllgs.sb3ss6securityuserrolejjwt.model.AuthenticationResponse;
import com.pblgllgs.sb3ss6securityuserrolejjwt.model.RegisterRequest;
import com.pblgllgs.sb3ss6securityuserrolejjwt.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *
 * @author pblgl
 * Created on 26-03-2024
 *
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
        return new ResponseEntity<>(authenticationService.authenticate(request), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> login(@RequestBody RegisterRequest request){
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.CREATED);
    }

}
