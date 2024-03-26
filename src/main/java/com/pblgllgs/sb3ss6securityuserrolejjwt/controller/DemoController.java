package com.pblgllgs.sb3ss6securityuserrolejjwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *
 * @author pblgl
 * Created on 26-03-2024
 *
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/admin")
    public ResponseEntity<String> admin(){
        return new ResponseEntity<>("ADMIN", HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<String> user(){
        return new ResponseEntity<>("USER", HttpStatus.OK);
    }
}
