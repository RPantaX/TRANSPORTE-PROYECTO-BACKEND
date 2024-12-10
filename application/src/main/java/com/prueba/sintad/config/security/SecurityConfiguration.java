package com.prueba.sintad.config.security;

import com.prueba.sintad.entity.security.Role;
import com.prueba.sintad.ports.in.security.UsuarioServiceIn;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final UsuarioServiceIn usuarioServiceIn;

    //decir que rutas estan protegidas y cuales no.
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        //descactivamos AbstractHttpConfigurer para evitar los errores de cors origin
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/authentication/**")
                        .permitAll()
//                        .requestMatchers(  HttpMethod.GET, "/api/v1/entidad/**").hasAnyAuthority(Role.USER.name(),Role.ADMIN.name())
//                        .requestMatchers(AUTH_WHITELIST).permitAll()
//                        .anyRequest().hasAnyAuthority(Role.ADMIN.name()))
                        .anyRequest().hasAnyAuthority(Role.USER.name(),Role.ADMIN.name())
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //carga nuestros datos del usuario
        daoAuthenticationProvider.setUserDetailsService(usuarioServiceIn.userDetailsServiceIn());
        //verifica la contraseña de los usuarios
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //orquesta la authentication de todos los proveedores configurados, o sea authenticationProvider que va a ser nuestro authenticator LA COMPARACION DE CONTRASEÑAS LA MANEJA authenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs*/**");
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // Permitir cualquier origen (modifica según tus necesidades)
        configuration.addAllowedMethod("*"); // Permitir todos los métodos HTTP
        configuration.addAllowedHeader("*"); // Permitir todos los encabezados

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica la configuración a todas las rutas
        return source;
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }
    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/api/v1/authentication/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/swagger-ui/**",
            "/swagger-ui.html",
    };
}
