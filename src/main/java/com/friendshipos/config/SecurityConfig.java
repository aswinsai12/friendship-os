package com.friendshipos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                // Public pages
                .requestMatchers("/", "/index.html", "/login.html", "/birthday.html", "/css/**", "/js/**", "/images/**").permitAll()

                // ADMIN only
                .requestMatchers("/dashboard.html").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/memory/upload").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/memory/**").hasRole("ADMIN")

                // Logged-in users can view memories
                .requestMatchers(HttpMethod.GET, "/memory/**").authenticated()

                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler((req, res, auth) -> {
                    if (auth.getAuthorities().stream()
                        .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
                        res.sendRedirect("/dashboard.html");
                    } else {
                        res.sendRedirect("/entrance.html");
                    }
                })
                .permitAll()
            )
            .logout(logout -> logout.logoutSuccessUrl("/login.html?logout"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
