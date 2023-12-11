package blog.backend.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable());
    // .antMatchers(HttpMethod.GET, "/api/v1/categories/all-categories").permitAll()
    // // Permite GET a /all-categories
    // // sin autenticación
    // .antMatchers("/api/v1/auth/**").permitAll() // Permitir todos los endpoints
    // bajo /api/v1/auth sin autenticación
    // .anyRequest().authenticated()
    // .and()
    // .sessionManagement().disable()
    // .addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

}
