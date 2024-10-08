package com.assignment.user.api;

import com.assignment.user.dto.RegistrationDto;
import com.assignment.user.entity.User;
import com.assignment.user.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequestMapping("/user")
public class UserApi {
    @Autowired
    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public List<User> findAll(){
        return userService.findAll();
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        try {
            updatedUser.setId(id);
            User updatedUserInfo = userService.updateUser(updatedUser);
            return ResponseEntity.ok(updatedUserInfo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete user: " + e.getMessage());
        }
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        try {
            Optional<User> userOptional = userService.findById(id);

            if (userOptional.isPresent()) {
                return ResponseEntity.ok(userOptional.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to get user: " + e.getMessage());
        }
    }
    @GetMapping("/byPage")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public Page<User> getAllUsersByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchKey,
            @RequestParam(required = false) Integer numericValue) {

        return userService.getAllUserByPage(page, size, searchKey, numericValue);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    public Page<User> getAllUsers(@RequestParam("searchKey")Optional<String> searchKey,
                                       @RequestParam(required = false,defaultValue = "0")int page,
                                       @RequestParam(required = false,defaultValue = "10")int size){
        return userService.search(page,size, searchKey);
    }
    @GetMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> saveUser(@RequestBody RegistrationDto body) {
        try {
            userService.saveUser(body);
            return ResponseEntity.ok("User saved successfully. "+ body);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to save user: " + e.getMessage());
        }
    }

//    @GetMapping("/byPage")
//    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
//    public ResponseEntity<Page<User>> getAllUserByPage(@RequestParam (defaultValue = "0") int page,
//                                                       @RequestParam (defaultValue = "10") int size) {
//        Page<User> userPage = userService.getAllUserByPage(page, size);
//        return ResponseEntity.ok(userPage);
//    }
}
