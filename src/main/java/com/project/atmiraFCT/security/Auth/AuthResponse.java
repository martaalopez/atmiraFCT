package com.project.atmiraFCT.security.Auth;

import com.project.atmiraFCT.model.domain.Colaborator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    String token;   // Token de autenticación
    String email;   // Correo electrónico del colaborador
    String name;    // Nombre del colaborador
    String surname; // Apellido del colaborador
    String id_alias; // ID del colaborador

    String role;    // Rol del colaborador
}
