package com.userapi.SystemSecurity;

import com.userapi.Repository.AppUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("userSecurity")
@AllArgsConstructor
public class UserSecurity {

    private final AppUserRepo userRepo;

    public boolean hasUserId(Authentication authentication, Long userId) {
        Long id = userRepo.findByUsername(authentication.getName()).get().getId();
        return Objects.equals(userId, id);
    }
}
