package com.project.atmiraFCT.security.Auth;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.WorkPlace;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.WorkPlaceRepository;
import com.project.atmiraFCT.security.Jwt.JwtService;
import com.project.atmiraFCT.security.User.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ColaboratorRepository colaboratorRepository;
    private final WorkPlaceRepository workPlaceRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

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
                .build();
    }

    public AuthResponse register(RegisterRequest request, Long workplaceId) {
        WorkPlace workplace = workPlaceRepository.findById(workplaceId)
                .orElseThrow(() -> new RecordNotFoundException("Workplace not found with id: " + workplaceId));

        Colaborator colaborator = Colaborator.builder()
                .id_alias(request.getId_alias())
                .email(request.getEmail())
                .isActive(request.getIsActive())
                .relaseDate(request.getRelaseDate())
                .hours(request.getHours())
                .guards(request.getGuards())
                .expense(request.getExpense())
                .name(request.getName())
                .surname(request.getSurname())
                .password(passwordEncoder.encode(request.getPassword()))
                .workPlace(workplace)
                .responsible(request.getResponsible())
                .role(Role.USER)
                .build();

        colaboratorRepository.save(colaborator);

        String token = jwtService.getToken(colaborator);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
}