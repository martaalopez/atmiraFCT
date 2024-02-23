package com.project.atmiraFCT.security.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

        private String id_alias; // Alias del usuario

        private String email; // Correo electrónico del usuario

        private String name; // Nombre del usuario

        private String surname; // Apellido del usuario

        private String password; // Contraseña del usuario
}
