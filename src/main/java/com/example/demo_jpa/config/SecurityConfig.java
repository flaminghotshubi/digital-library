package com.example.demo_jpa.config;

import com.example.demo_jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder getPE() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(getPE());
        authProvider.setUserDetailsService(userService);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authz) -> authz
                        // book api
                        .requestMatchers("/books/search/**").hasAuthority("search-books") // admin, student
                        .requestMatchers("/books/**").hasAuthority("update-book") // admin

                        // student api
                        .requestMatchers("/student/details/**").hasAuthority("get-profile") // student
                        .requestMatchers(HttpMethod.POST, "/student/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/student/**").hasAuthority("get-student-profile") // admin
                        .requestMatchers("/student/**").hasAuthority("update-profile") // student

                        // admin api
                        .requestMatchers(HttpMethod.POST, "/admin/**").permitAll() // ideally this is wrong
                        // admin should add an admin, it should not be open to everyone

                        // transaction api
                        .requestMatchers(HttpMethod.GET, "/transaction/**").hasAuthority("get-transaction") // admin
                        .requestMatchers("/transaction/**").hasAuthority("create-transaction") // student
                        .anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);

        //most restricted --> least restricted
        return http.build();
    }
}
