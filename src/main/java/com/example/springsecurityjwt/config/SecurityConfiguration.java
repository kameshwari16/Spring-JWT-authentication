// package com.example.springsecurityjwt.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.DefaultSecurityFilterChain;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.example.springsecurityjwt.Entities.Role;
// import com.example.springsecurityjwt.Services.UserService;

// import jakarta.el.ELException;
// import lombok.RequiredArgsConstructor;

// @Configuration
// @EnableWebSecurity// enable web security features and configure security for web applications.
// @RequiredArgsConstructor
// public class SecurityConfiguration {
//     private final JwtAuthenticationFilter jwtAuthenticationFilter;
//     private final UserService userService;

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         return http
//                 .csrf(AbstractHttpConfigurer::disable)//Cross-Site Request Forgery (CSRF) is a type of security attack where an unauthorized command is transmitted from a user that the web application trusts. 
//                 .authorizeHttpRequests(
//                         req->req.requestMatchers("/api/v1/auth/**").permitAll()//Allows all requests to paths starting with /api/v1/auth/ without authentication.
//                                 .requestMatchers("/api/v1/admin").hasAuthority("ADMIN")//Restricts access to /api/v1/admin to users with the ADMIN authority.
//                                 .requestMatchers("/api/v1/user").hasAuthority("USER")//Allows access to /api/v1/user to users with the USER role.
//                                 .anyRequest().authenticated()//All other requests require authentication
//                 )
//                 .sessionManagement(session->session
//                         .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                         .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)//This ensures that the JWT is checked before processing username/password authentication.
//                         .build();
//     }  
//     private AuthenticationProvider authenticationProvider() {
//         DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();//DAO (Data Access Object) pattern.
//         authenticationProvider.setUserDetailsService(userService.userDetailsService());
//         authenticationProvider.setPasswordEncoder(passwordEncoder());
//         return authenticationProvider;
//     }
//     @Bean
//     public PasswordEncoder passwordEncoder(){
//         return new BCryptPasswordEncoder();//hash the user password(secure password hashing algorithm)
//     }
//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception{
//         return config.getAuthenticationManager();

//     }
    
// }



package com.example.springsecurityjwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.example.springsecurityjwt.Entities.Role;
import com.example.springsecurityjwt.Services.UserService;
// import com.example.springsecurityjwt.Services.impl.UserSericeImpl;
import com.example.springsecurityjwt.Services.impl.UserServiceImpl;

import jakarta.el.ELException;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity(debug = true)// enable web security features and configure security for web applications.
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    // private final UserService userService;
    private final UserServiceImpl userServiceImpl;
    private final LogoutHandler customLogoutHandler; // Inject a valid LogoutHandler
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .requestMatchers("/api/v1/admin").hasAuthority("ADMIN")
                    // .requestMatchers("/api/v1/user").hasAuthority("USER")
                    .requestMatchers("/api/v1/auth/logout").permitAll()
                    .requestMatchers("/api/v1/both").permitAll()
                    .anyRequest().authenticated())
                    .userDetailsService(userServiceImpl)
                    .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(customLogoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                                .invalidateHttpSession(true)
                                // .deleteCookies("JSESSIONID")
                )
                .build();
    }
    private AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();//DAO (Data Access Object) pattern.
        authenticationProvider.setUserDetailsService(userServiceImpl);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();//hash the user password(secure password hashing algorithm)
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception{
        return config.getAuthenticationManager();

    }
    
}
