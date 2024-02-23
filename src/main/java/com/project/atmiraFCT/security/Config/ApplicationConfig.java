package com.project.atmiraFCT.security.Config;

import com.project.atmiraFCT.repository.ColaboratorRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final ColaboratorRepository colaboratorRepository;

    /**
     * Configura el gestor de autenticación.
     *
     * @param config    La configuración de autenticación.
     * @return          El gestor de autenticación configurado.
     * @throws Exception Si hay un error al configurar el gestor de autenticación.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }

    /**
     * Configura el proveedor de autenticación.
     *
     * @return El proveedor de autenticación configurado.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Configura el encriptador de contraseñas.
     *
     * @return El encriptador de contraseñas configurado.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura el servicio de detalles de usuario.
     *
     * @return El servicio de detalles de usuario configurado.
     */
    @Bean
    public UserDetailsService userDetailService() {
        return username -> (UserDetails) colaboratorRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
