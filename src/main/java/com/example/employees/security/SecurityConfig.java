package com.example.employees.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

//    add support for JDBC authentication
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        userDetailsManager.setUsersByUsernameQuery("SELECT user_id, password, active FROM members WHERE user_id = ?");
        userDetailsManager.setAuthoritiesByUsernameQuery("SELECT user_id, role FROM roles WHERE user_id = ?");
        return userDetailsManager;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/docs/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/h2_console/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/employees").hasAnyRole("ADMIN", "MANAGER", "EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/v1/employees/**").hasAnyRole("ADMIN", "MANAGER", "EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/v1/employees/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/v1/employees/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/v1/employees/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(HttpBasicCustomizer -> HttpBasicCustomizer.disable())
                .httpBasic(Customizer.withDefaults())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint()));
        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {

        return (request, response, authException) -> {
            // Send 401 unauthorized status without triggering a basic auth
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");

            // Removes the WWW-Authenticate header to prevent browser popup
            response.setHeader("WWW-Authenticate", "");

            response.getWriter().write("{\"error\": \"Unauthorized access\"}");
        };
    }

}
