package com.restapijwt.controller;

import com.restapijwt.aop.Check;
import com.restapijwt.aop.CurrentUser;
import com.restapijwt.entity.User;
import com.restapijwt.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @PreAuthorize("hasAuthority('ADD_COMPANY')")
    @GetMapping("/me")
    public ResponseEntity getMe() throws ResourceNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user == null) {
            throw new ResourceNotFoundException("User topilmadi!");
        }
        return ResponseEntity.ok(user);
    }

    @Check(value = "ADD_COMPANY")
//    @PreAuthorize("hasAuthority('ADD_COMPANY')")
    @GetMapping
    public ResponseEntity getMe(@CurrentUser User user) {
        return ResponseEntity.ok(user);
    }
}
