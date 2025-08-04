package br.com.riachuelo.desafio_otavio.configs;

import br.com.riachuelo.desafio_otavio.adapters.repositories.UsersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfiguration {

    private final UsersRepository usersRepository;

    public ApplicationConfiguration(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usersRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        return authentication -> {
            var user = userDetailsService.loadUserByUsername(authentication.getName());

            if (!passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
                throw new BadCredentialsException("Credenciais inválidas");
            }

            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        };
    }
}