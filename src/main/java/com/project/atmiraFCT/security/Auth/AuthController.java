package com.project.atmiraFCT.security.Auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }
}
