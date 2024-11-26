package io.github.ndimovt.RelationTesting.configs;

import io.github.ndimovt.RelationTesting.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The class ApplicationConfiguration
 */
@Configuration
public class ApplicationConfiguration {
    private final UserRepository userRepository;

    /**
     * Instantiating ApplicationConfiguration
     * @param userRepository UserRepository object
     */

    public ApplicationConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * If user with given username is found returns UserDetails object, else exception is thrown
     * @return UserDetails instance or UsernameNotFoundException
     */

    @Bean
    UserDetailsService userDetailsService(){
        return username -> (UserDetails) userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    /**
     * Returns BCryptPasswordEncoder instance
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    BCryptPasswordEncoder encoder (){
        return new BCryptPasswordEncoder();
    }

    /**
     * Returns AuthenticationManager object
     * @param config AuthenticationConfiguration object
     * @return AuthenticationManager instance
     * @throws Exception Exception in case of improper return of AuthenticationConfiguration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Returns DaoAuthenticationProvider with userdetails and encoded password
     * @return DaoAuthenticationProvider instance
     */
    @Bean
    AuthenticationProvider authenticationProvider (){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(encoder());
        return daoAuthenticationProvider;
    }


}
