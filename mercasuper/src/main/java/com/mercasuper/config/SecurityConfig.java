package com.mercasuper.config;

import com.mercasuper.config.UserDetailsServiceImpl; // Importa tu servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService; // Inyectamos tu servicio de base de datos

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // // Este bloque es el "puente" que une tu DB con el login
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // usa el buscador de correo
        authProvider.setPasswordEncoder(passwordEncoder()); // usa el encriptador
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
    // 1. Recursos estáticos primero
        .requestMatchers("/", "/registro", "/registrar", "/css/**", "/js/**", "/img/**").permitAll()
    
    // 2. Rutas de Gestión (SOLO ADMIN) - Aseguramos que sean prioritarias
        .requestMatchers("/productos/nuevo/**").hasRole("ADMIN")
        .requestMatchers("/productos/editar/**").hasRole("ADMIN")
        .requestMatchers("/productos/eliminar/**").hasRole("ADMIN")
        .requestMatchers("/productos/**").hasRole("ADMIN")
        .requestMatchers("/productos/guardar").hasRole("ADMIN")
    
    // 3. Rutas de Cliente y API (Ambos roles)
        .requestMatchers("/ventas/**", "/api/productos/**", "/tienda/**").hasAnyRole("ADMIN", "USER")
    
    // 4. Todo lo demás requiere estar logueado
        .anyRequest().authenticated()
)
            .formLogin(login -> login
                .loginPage("/")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/verificar-rol", true)
                .failureUrl("/?error=true") //muestra un mensaje rojo en caso de fallo
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }
}