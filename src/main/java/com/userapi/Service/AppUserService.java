package com.userapi.Service;

import com.userapi.Repository.AppUserRepo;
import com.userapi.dto.RegisterUser;
import com.userapi.entity.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with username ' %s ' not found";
    private final AppUserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public AppUser loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, s)));
    }

    public AppUser signupAppUser(RegisterUser registerUser){
        AppUser newUser = new AppUser(
                registerUser.getFirstName(),
                registerUser.getLastName(),
                registerUser.getUsername(),
                registerUser.getEmail(),
                passwordEncoder.encode(registerUser.getPassword()),
                registerUser.getRoles()
        );

        return userRepo.save(newUser);
    }

    public List<AppUser> getAllUsers(){
        return userRepo.findAll();
    }

    public Optional<AppUser> getSingleUserById(Long id){
        return userRepo.findById(id);
    }

    public Optional<AppUser> getSingleUserByUsername(String username){
        return userRepo.findByUsername(username);
    }


    public void updateAppUser(Long id, AppUser user){
        try {
            user.setId(id);
            userRepo.save(user);
        }catch (Exception err){
            System.out.println(err.getMessage());
        }
    }

    public void deleteAppUser(Long id){
        try {
            userRepo.deleteById(id);
        }catch (Exception err){
            System.out.println(err.getMessage());
        }
    }
}
