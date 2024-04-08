package com.project.atmiraFCT.security.Auth;


import com.project.atmiraFCT.exception.Validator;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.WorkPlaceRepository;
import com.project.atmiraFCT.security.Jwt.JwtService;
import com.project.atmiraFCT.security.User.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ColaboratorRepository colaboratorRepository;
    private final WorkPlaceRepository workPlaceRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Método para realizar el inicio de sesión.
     *
     * @param request El objeto LoginRequest que contiene los detalles del inicio de sesión.
     * @return Un objeto AuthResponse con el token de autenticación y la información del usuario.
     * @throws BadCredentialsException si las credenciales son inválidas.
     * @throws UsernameNotFoundException si el usuario no se encuentra en la base de datos.
     */
    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        Colaborator userDetails = colaboratorRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String token = jwtService.getToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .email(userDetails.getEmail())
                .name(userDetails.getName())
                .id_alias(userDetails.getId_alias())
                .surname(userDetails.getSurname())
                .role(userDetails.getRole().name())
                .build();
    }

    /**
     * Método para registrar un nuevo usuario.
     *
     * @param request El objeto RegisterRequest contiene los detalles del usuario a registrar.
     * @return Un objeto AuthResponse con el token de autenticación y la información del usuario registrado.
     * @throws IllegalArgumentException si la dirección de correo electrónico no es válida.
     */
    public AuthResponse register(RegisterRequest request) {
        String email = request.getEmail();

        if (!Validator.isValidGmail(email)) {
            throw new IllegalArgumentException("La dirección de correo electrónico no es válida.");
        }

        Colaborator colaborator = Colaborator.builder()
                .id_alias(request.getId_alias())
                .email(request.getEmail())
                .name(request.getName())
                .surname(request.getSurname())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        colaboratorRepository.save(colaborator);
        String token = jwtService.getToken(colaborator);
        return AuthResponse.builder()
                .token(token)
                .email(colaborator.getEmail())
                .name(colaborator.getName())
                .id_alias(colaborator.getId_alias())
                .surname(colaborator.getSurname())
                .role(colaborator.getRole().name())
                .build();
    }
}
