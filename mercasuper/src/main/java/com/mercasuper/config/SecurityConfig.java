package com.mercasuper.config;

import com.mercasuper.config.UserDetailsServiceImpl;
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
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitado para facilitar pruebas en local
            .authorizeHttpRequests(auth -> auth
                // Diseño e Imágene
                .requestMatchers("/", "/registro", "/registrar", "/css/**", "/js/**", "/img/**").permitAll()
                
                //ACCESO API
                .requestMatchers("/api/productos/**").permitAll()
                
                // RUTAS DE LOS ROLESs
                .requestMatchers("/productos/**").hasRole("ADMIN")
                .requestMatchers("/ventas/**", "/tienda/**").hasAnyRole("ADMIN", "USER")
                
                // PROTEGIDO
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/verificar-rol", true)
                .failureUrl("/?error=true")
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