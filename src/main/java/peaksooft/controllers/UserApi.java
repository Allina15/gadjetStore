package peaksooft.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksooft.dto.*;
import peaksooft.services.UserService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApi {

    private final UserService userService;

    @GetMapping
    @PermitAll
    public UsersResponse getAll(@RequestParam int currentPage, @RequestParam int size){
       return userService.getAll(currentPage,size);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable long id){
        return userService.delete(id);
    }

    @PermitAll
    @GetMapping("/{id}")
    public UserResponse getProfile(@PathVariable long id){
        return userService.getProfile(id);
    }

    @PutMapping("/{id}")
    public UserResponse update(@Valid @PathVariable long id, @RequestBody UserRequest userRequest, Principal principal){
        return userService.update(id,userRequest, principal);
    }
}
