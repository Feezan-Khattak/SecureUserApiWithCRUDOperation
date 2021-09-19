package com.userapi.dto;

import com.userapi.SystemSecurity.AppUserRole;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegisterUser {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private AppUserRole roles;

}
