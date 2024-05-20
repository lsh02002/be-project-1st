package me.seho.beproject1st.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig2 {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth)->
                        auth
                                .requestMatchers(HttpMethod.GET,
                                        "/api/posts/count/**",
                                        "/api/posts/search**",
                                        "/api/posts/**",
                                        "/api/get-post/**",
                                        "/api/comments/**",
                                        "/api/likes/**",
                                        "/api/set-likes/**",
                                        "/api/comments-by-user-email/**",
                                        "/api/get-likes-by-user-id/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**")
                                        .permitAll()
                                .requestMatchers(HttpMethod.POST,
                                        "/api/login/**",
                                        "/api/signup/**",
                                        "/api/posts/**",
                                        "/api/comments/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.PUT,
                                        "/api/posts/**",
                                        "/api/comments/**")
                                .permitAll());

        return http.build();
    }
}
