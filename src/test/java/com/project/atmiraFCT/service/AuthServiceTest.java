package com.project.atmiraFCT.service;
import com.project.atmiraFCT.exception.Validator;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.WorkPlaceRepository;
import com.project.atmiraFCT.security.Auth.AuthResponse;
import com.project.atmiraFCT.security.Auth.AuthService;
import com.project.atmiraFCT.security.Auth.LoginRequest;
import com.project.atmiraFCT.security.Auth.RegisterRequest;
import com.project.atmiraFCT.security.Jwt.JwtService;
import com.project.atmiraFCT.security.User.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private ColaboratorRepository colaboratorRepository;

    @Mock
    private WorkPlaceRepository workPlaceRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private Colaborator colaborator;
    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        colaborator = Colaborator.builder()
                .id_alias("1")
                .email("john.doe@example.com")
                .name("John")
                .surname("Doe")
                .password("password123")
                .role(Role.USER)
                .build();

        loginRequest = new LoginRequest("john.doe@example.com", "password123");

        registerRequest = new RegisterRequest("john.doe@example.com", "password123", "John", "Doe", "1");
    }

    @DisplayName("Test login with valid credentials")
    @Test
    void testLoginWithValidCredentials() {
        // Given
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(colaboratorRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(colaborator));
        when(jwtService.getToken(colaborator)).thenReturn("token");

        // When
        AuthResponse response = authService.login(loginRequest);

        // Then
        assertThat(response.getToken()).isEqualTo("token");
        assertThat(response.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(response.getName()).isEqualTo("John");
        assertThat(response.getId_alias()).isEqualTo("1");
        assertThat(response.getSurname()).isEqualTo("Doe");
        assertThat(response.getRole()).isEqualTo(Role.USER.name());
    }

    @DisplayName("Test login with invalid credentials")
    @Test
    void testLoginWithInvalidCredentials() {
        // Given
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Credenciales inválidas"));

        // Then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(BadCredentialsException.class);
    }

    @DisplayName("Test login with user not found")
    @Test
    void testLoginWithUserNotFound() {
        // Given
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(colaboratorRepository.findByEmail("johndoe@gmail.com")).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(UsernameNotFoundException.class);
    }

    @DisplayName("Test register with valid request")
    @Test
    void testRegisterWithValidRequest() {
        // Given
        when(colaboratorRepository.save(any())).thenReturn(colaborator);
        when(jwtService.getToken(colaborator)).thenReturn("token");

        // When
        AuthResponse response = authService.register(registerRequest);

        // Then
        assertThat(response.getToken()).isEqualTo("token");
        assertThat(response.getEmail()).isEqualTo("johndoe@gmail.com");
        assertThat(response.getName()).isEqualTo("John");
        assertThat(response.getId_alias()).isEqualTo("1");
        assertThat(response.getSurname()).isEqualTo("Doe");
        assertThat(response.getRole()).isEqualTo(Role.USER.name());
    }

    @DisplayName("Test register with invalid email")
    @Test
    void testRegisterWithInvalidEmail() {
        // Given
        RegisterRequest invalidEmailRequest = new RegisterRequest("john.doeexample.com", "password123", "John", "Doe", "1");

        // Then
        assertThatThrownBy(() -> authService.register(invalidEmailRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

