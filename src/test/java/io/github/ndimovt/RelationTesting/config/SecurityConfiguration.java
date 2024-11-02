package io.github.ndimovt.RelationTesting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
            httpSecurity.csrf(AbstractHttpConfigurer::disable).
                    authorizeRequests(authorize ->
                            authorize.
                                    requestMatchers("/book/byAuthorName/**", "/register/**").
                                    permitAll().
                                    anyRequest().
                                    authenticated());
            return httpSecurity.build();
    }
}
