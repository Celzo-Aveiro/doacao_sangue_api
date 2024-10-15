package celzoo.project.bloood.donation.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desabilita CSRF, útil para APIs REST que não armazenam sessão
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configura CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login**").permitAll() // Permite acesso público a URLs que começam com /login/
                        .anyRequest().authenticated() // Qualquer outra requisição precisa de autenticação
                )
                .httpBasic(httpBasic -> httpBasic.realmName("My App Realm")) // Configura HTTP Basic com um realm name
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Adiciona o filtro JWT antes do filtro de autenticação

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("admin")
                .password("{noop}admin") // Adicione {noop} antes da senha
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://doacao-sangue-front-j5s8rtukm-celzo-aveiros-projects.vercel.app")); // Permite acesso apenas da porta 4200
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // Métodos permitidos
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Cabeçalhos permitidos
        configuration.setAllowCredentials(true); // Permite credenciais
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica a configuração a todas as URLs
        return source;
    }
}