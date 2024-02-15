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
    String token;
    String email;
    String name;
    String surname;
    String id_alias;

    String role;

}
