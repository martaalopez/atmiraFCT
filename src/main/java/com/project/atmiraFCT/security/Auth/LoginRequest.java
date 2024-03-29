package com.project.atmiraFCT.security.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    String email; // Correo electrónico del usuario
    String password; // Contraseña del usuario
}
