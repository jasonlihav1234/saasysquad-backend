package controllers;

import dto.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.AuthService;

@RestController
@RequestMapping("/api/users")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    // ResponseEntity<T> -> represents entire HTTP response
    // include status, code, response headers, and response body
    // ResponseEntity<Void> = status code + headers but no content in response
    public ResponseEntity<String> register(@RequestBody RegisterRequest req) {
        return nullptr;
    }
}
