package com.pblgllgs.sb3ss6securityuserrolejjwt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/*
 *
 * @author pblgl
 * Created on 26-03-2024
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private HttpStatus httpStatus;
    private String errorMessage;
    private Object body;

    public ErrorResponse(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    public int getStatusCodeValue(){
        return httpStatus.value();
    }
}
