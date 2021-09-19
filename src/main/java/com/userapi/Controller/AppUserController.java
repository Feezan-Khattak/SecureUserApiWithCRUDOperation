package com.userapi.Controller;

import com.userapi.Service.AppUserService;
import com.userapi.dto.RegisterUser;
import com.userapi.entity.AppUser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/user/api/")
public class AppUserController {

    private final AppUserService userService;

    @ApiOperation(value = "Post request to register new User",
            notes = "This post request will take the RegisterUser object which is a dto class to take important " +
                    "data from the user")
    @PostMapping("newUser")
    ResponseEntity<AppUser> saveAppUser(@RequestBody RegisterUser registerUser) {
        AppUser newUser = userService.signupAppUser(registerUser);
        if (newUser == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(newUser);
        }
    }

    @ApiOperation(value = "This is the Get method for the admin",
            notes = "This get request will get all the users from the database, and this operation will done only by the admin")
    @GetMapping("users")
    ResponseEntity<List<AppUser>> getUsers() {
        List<AppUser> allUsers = userService.getAllUsers();
        if (allUsers.size() < 1) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(allUsers);
        }
    }

    @ApiOperation(value = "The get request to find user by username",
            notes = "This get request will take the username in a request and will return the relative data" +
                    " of that user")
    @GetMapping("user/details")
    ResponseEntity<AppUser> getUser(
            @ApiParam(value = "This username must be valid and in string form to get the correct user", required = true)
            Authentication authentication) {
        AppUser appUser = userService.loadUserByUsername(authentication.getName());
        if (appUser == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(appUser);
        }
    }

//    @PreAuthorize("@userSecurity.hasUserId(authentication, #userId)")
    @GetMapping("user/{userId}")
    ResponseEntity<AppUser> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok().body(userService.getSingleUserById(userId).get());
    }

    @ApiOperation(value = "This is update Request which is only allowed for the admin",
            notes = "This update request will take the id of user and the object of the updated user to update the user" +
                    "record inside the database")
    @PutMapping("updateUser/{id}")
    ResponseEntity<AppUser> updateUser(
            @ApiParam(value = "This is the required user id which must be provide by the admin" +
                    " in order the update user correctly, this id must be in integer", required = true)
            @PathVariable Long id,
            @RequestBody AppUser user) {
        try {
            userService.updateAppUser(id, user);
            return ResponseEntity.ok(user);
        } catch (Exception err) {
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation(value = "This is the delete request for the admin only",
            notes = "This delete request will take the user id which is only provided by the admin to successfully delete" +
                    "the user record from the database")
    @DeleteMapping("deleteUser/{id}")
    ResponseEntity<String> deleteUser(
            @ApiParam(value = "This is the required user id which must be provide by the admin" +
                    " in order the delete user correctly, this id must be in integer", required = true)
            @PathVariable Long id) {
        try {
            userService.deleteAppUser(id);
            return ResponseEntity.ok("App User deleted successfully..");
        } catch (Exception er) {
            return ResponseEntity.notFound().build();
        }
    }
}
