package io.github.ndimovt.RelationTesting.configs;

import io.github.ndimovt.RelationTesting.roles.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * The class SecurityConfiguration
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter authFilter;

    /**
     * Instantiates SecurityConfiguration
     * @param authenticationProvider AuthenticationProvider object
     * @param authFilter JwtAuthenticationFilter object
     */
    public SecurityConfiguration(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter authFilter) {
        this.authenticationProvider = authenticationProvider;
        this.authFilter = authFilter;
    }

    /**
     * Authorizes endpoints, defines session state, filters the request before build.
     * @param httpSecurity HttpSecurity object
     * @return SecurityFilterChain object
     * @throws Exception Thrown if filterChain fails
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.
                csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(authorize -> authorize.
                        requestMatchers("book/byAuthorName/**").hasAnyAuthority(Roles.USER.name(), Roles.ADMIN.name()).
                        requestMatchers("/book/delete/**", "/author/add").hasAuthority(Roles.ADMIN.name()).
                        requestMatchers("/auth/**").permitAll()
                ).
                sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                authenticationProvider(authenticationProvider).
                addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class).
                build();
    }

    /**
     * Sets allowed origins, methods and headers
     * @return UrlBasedCorsConfigurationSource instance
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:8086"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
