// package edu.tcu.cs.superfrogscheduler.system;


// import org.springframework.beans.factory.annotation.Autowired;

// import org.springframework.context.annotation.Configuration;

// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration
// public class SecurityConfig extends WebSecurityConfigurerAdapter {

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http
//             .authorizeRequests()
//             .antMatchers("/api/admin/**").hasRole("ADMIN")
//             .antMatchers("/api/students/**", "/api/events/**").hasRole("STUDENT")
            
//             .antMatchers("/api/appearances/**").permitAll()
//             .antMatchers("/api/spirit-director-events/**").permitAll()

//             .anyRequest().authenticated()
//             .and()
//             .httpBasic()
//             .and()
//             .csrf().disable();
//     }
// }

package edu.tcu.cs.superfrogscheduler.system;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .authorizeRequests()
            .antMatchers("/api/admin/**").hasRole("ADMIN")
            .antMatchers("/api/students/**", "/api/events/**").hasRole("STUDENT")
            
            .antMatchers("/api/appearances/**").permitAll()
            .antMatchers("/api/spirit-director-events/**").permitAll()

            .anyRequest().authenticated()
            .and()
            .httpBasic()
            .and()
            .csrf().disable();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Use allowedOriginPatterns to specify a pattern instead of setAllowedOrigins when allowCredentials is true
        configuration.setAllowedOriginPatterns(Arrays.asList("*")); // Adjust this pattern or list specific allowed origins
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true); // Allowing credentials

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
        }
}