package com.pblgllgs.sb3ss6securityuserrolejjwt.model;
/*
 *
 * @author pblgl
 * Created on 26-03-2024
 *
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    private String email;
    private String password;
}
