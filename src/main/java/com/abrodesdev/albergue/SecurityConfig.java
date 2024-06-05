package com.abrodesdev.albergue;

import com.abrodesdev.albergue.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;


@EnableWebSecurity
@Configuration

public class SecurityConfig{


    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuracion de CORS para permitir peticiones desde cualquier origen
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
            }
        };
    }

    // Configuracion de Spring Security para autenticacion de usuarios
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    // Configuracion de Spring Security para autorizacion de peticiones
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configuracion de autenticacion basica
                .httpBasic(withDefaults())
                // Configuracion de autorizacion de peticiones
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // Permitir peticiones a los endpoints de login registro y recuperar contraseña sin autenticacion
                                .requestMatchers(HttpMethod.POST,"/usuarios/login").anonymous()
                                .requestMatchers(HttpMethod.POST,"/usuarios/recuperarContrasena").anonymous()
                                .requestMatchers(HttpMethod.POST,"/usuarios/registrar").anonymous()
                                .anyRequest().authenticated()

                )
                // Configuracion de sesiones sin estado
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Deshabilitar CSRF
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
