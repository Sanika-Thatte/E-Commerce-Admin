package com.example.commerce.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.commerce.service.CustomAdminDetailsService;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private CustomAdminDetailsService userDetailsService;
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	    return config.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable()) 

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index",  "/adminLogin", "/adminSignUp","/css/**","/js/**").permitAll()
                .requestMatchers("/adminDashboard" ,"/searchAdminProduct" , "/addProduct" , "/deleteProduct" ,"/editProduct", "/updateProduct").authenticated()
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/adminLogin")
                .loginProcessingUrl("/adminLogin")
                .defaultSuccessUrl("/adminDashboard", true)
                .failureUrl("/adminLogin?error=true")
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/adminLogout")   
                .logoutSuccessUrl("/adminLogin?logout=true")
                .permitAll()
            );

        return http.build();
    }
}
