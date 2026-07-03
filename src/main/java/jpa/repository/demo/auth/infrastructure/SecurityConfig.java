package jpa.repository.demo.auth.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Value("${api.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf->csrf.disable())
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/", "/index.html", "/*.css", "/*.js", "/favicon.ico").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/ordemdeservico", "/ordemdeservico/**").hasAuthority("ROLE_ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/tecnico", "/tecnico/**").hasAuthority("ROLE_ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/servico", "/servico/**").hasAuthority("ROLE_ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/equipamento", "/equipamento/**").hasAuthority("ROLE_ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/cliente", "/cliente/**").hasAuthority("ROLE_ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/ordemdeservico", "/ordemdeservico/**").hasAuthority("ROLE_ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/tecnico", "/tecnico/**").hasAuthority("ROLE_ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/servico", "/servico/**").hasAuthority("ROLE_ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/equipamento", "/equipamento/**").hasAuthority("ROLE_ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/cliente", "/cliente/**").hasAuthority("ROLE_ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/ordemdeservico", "/ordemdeservico/**").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_TECNICO")
                        .requestMatchers(HttpMethod.GET, "/tecnico", "/tecnico/**").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_TECNICO")
                        .requestMatchers(HttpMethod.GET, "/servico", "/servico/**").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_TECNICO")
                        .requestMatchers(HttpMethod.GET, "/equipamento", "/equipamento/**").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_TECNICO")
                        .requestMatchers(HttpMethod.GET, "/cliente", "/cliente/**").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_TECNICO")
                        .requestMatchers(HttpMethod.DELETE, "/ordemdeservico", "/ordemdeservico/**").hasAuthority("ROLE_ADMINISTRADOR")
                        .requestMatchers(HttpMethod.DELETE, "/tecnico", "/tecnico/**").hasAuthority("ROLE_ADMINISTRADOR")
                        .requestMatchers(HttpMethod.DELETE, "/servico", "/servico/**").hasAuthority("ROLE_ADMINISTRADOR")
                        .requestMatchers(HttpMethod.DELETE, "/equipamento", "/equipamento/**").hasAuthority("ROLE_ADMINISTRADOR")
                        .requestMatchers(HttpMethod.DELETE, "/cliente", "/cliente/**").hasAuthority("ROLE_ADMINISTRADOR")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.stream(allowedOrigins).map(String::trim).toList());
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
