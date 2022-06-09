package com.innowise.secret_santa.config;

import com.innowise.secret_santa.security.JwtConfigurer;
import com.innowise.secret_santa.security.JwtToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
public class SecurityConfig {

    private final JwtToken jwtToken;
    private static final String REGISTRATION = "/api/registration";
    private static final String LOGIN = "/api/login";
    private static final String LOGOUT = "/api/logout";

    public SecurityConfig(JwtToken jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/spring-security-oauth-resource/**",
                        "/test").permitAll()
                .antMatchers(HttpMethod.POST, REGISTRATION).permitAll()
                .antMatchers(HttpMethod.POST, LOGIN).permitAll()
                .antMatchers(HttpMethod.POST, LOGOUT).authenticated()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtToken))
                .and().build();
    }

    @Bean

    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}