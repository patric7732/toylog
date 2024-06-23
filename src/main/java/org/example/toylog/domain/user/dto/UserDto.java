package org.example.toylog.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private String loginId;
    private String email;
    private String name;
    private String password;
}
