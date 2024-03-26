package com.pblgllgs.sb3ss6securityuserrolejjwt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *
 * @author pblgl
 * Created on 26-03-2024
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private String mobileNumber;
}
