package com.saasysquad.backend_tings.controllers;

import com.saasysquad.backend_tings.dto.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.saasysquad.backend_tings.services.AuthService;

@RestController
@RequestMapping("/api/users")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    // ResponseEntity<T> -> represents entire HTTP response
    // include status, code, response headers, and response body
    // ResponseEntity<Void> = status code + headers but no content in response
    public ResponseEntity<Void> register(@RequestBody RegisterRequest req) {
        String email = req.email();
        String password = req.password();
        String username = req.username();

        authService.register(email, password, username);

        // uses builder pattern if you want to return nothing
        // need .body if you want to return something
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/testroute")
    public ResponseEntity<String> test() {
        return ResponseEntity.status(HttpStatus.OK).body("This is a test");
    }
}
