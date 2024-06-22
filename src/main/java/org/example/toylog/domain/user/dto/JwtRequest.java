package org.example.toylog.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest {
    private String userId;
    private String password;
    private String name;
    private String email;
}