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
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private String userName;
    private String email;
}
