package edu.tcu.cs.superfrogscheduler.system;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()

            .antMatchers("/api/appearances/**").permitAll()

            .antMatchers("/api/admin/**").hasRole("ADMIN")
            .antMatchers("/api/students/**", "/api/events/**").hasRole("STUDENT") 
            .anyRequest().authenticated()
            .and()
            .httpBasic()
            .and()
            .csrf().disable();
    }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     return http
    //             .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
    //                     .requestMatchers(HttpMethod.GET, "api/appearances/**").permitAll()
    //                     .requestMatchers(HttpMethod.POST, "api/appearances/**").permitAll()
    //                     .requestMatchers(HttpMethod.PUT, "api/appearances/**").permitAll()
    //                     .requestMatchers(HttpMethod.DELETE, "api/appearances/**").permitAll()
    //                     .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll() // Explicitly fallback to antMatcher inside requestMatchers.
    //                     // Disallow everything else.
    //                     .anyRequest().authenticated() // Always a good idea to put this as last.
    //             )
    //             .headers(headers -> headers.frameOptions().disable()) // This is for H2 browser console access.
    //             .csrf(csrf -> csrf.disable())
    //             .cors(Customizer.withDefaults())
    //             .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(this.customBasicAuthenticationEntryPoint))
    //             .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt().and()
    //                     .authenticationEntryPoint(this.customBearerTokenAuthenticationEntryPoint)
    //                     .accessDeniedHandler(this.customBearerTokenAccessDeniedHandler))
    //             /* Configures the spring boot application as an OAuth2 Resource Server which authenticates all
    //              the incoming requests (except the ones excluded above) using JWT authentication.
    //              */
    //             .sessionManagement(sessionManagement ->
    //                     sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //             .build();
    // }
}

