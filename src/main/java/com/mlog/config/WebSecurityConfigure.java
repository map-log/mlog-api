package com.mlog.config;

import com.mlog.security.*;
import com.mlog.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfigure {

    private final Jwt jwt;

    private final JwtTokenConfigure jwtTokenConfigure;

    private final JwtAccessDeniedHandler accessDeniedHandler;

    private final EntryPointUnauthorizedHandler unauthorizedHandler;

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter(jwtTokenConfigure.getHeader(), jwt);
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(UserService userService) {
        return new JwtAuthenticationProvider(userService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .headers(header -> header.disable())

                .exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(unauthorizedHandler))

                .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeRequests(auth -> auth
                        .requestMatchers("/api/users/login").permitAll()
                        .requestMatchers("/api/products/**").permitAll()
                        .requestMatchers("/api/**").hasRole("ROLE_USER")
                        .anyRequest().permitAll())

                .formLogin(form -> form.disable());

        http
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
