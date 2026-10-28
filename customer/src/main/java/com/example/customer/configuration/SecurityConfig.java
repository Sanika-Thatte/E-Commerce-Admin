package com.example.customer.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.customer.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
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
                .requestMatchers("/", "/index",  "/userLogin", "/userSignUp", "/userDashboard" ,
                		"/searchUserProduct" ,"/viewProduct", "/searchBar",
                		"/uploads/**" , "/css/**",
                        "/images/**", "/js/**").permitAll()
                .requestMatchers("/addToCart", "/orderProduct").authenticated()
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/userLogin")
                .loginProcessingUrl("/userLogin")
                .defaultSuccessUrl("/userDashboard", true)
                .failureUrl("/userLogin?error=true")
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/userLogout")   
                .logoutSuccessUrl("/userLogin?logout=true")
                .permitAll()
            );

        return http.build();
    }
}
