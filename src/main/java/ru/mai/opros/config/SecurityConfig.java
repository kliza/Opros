package ru.mai.opros.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           HandlerMappingIntrospector introspector) throws Exception {

        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http
                .csrf(Customizer.withDefaults())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                                mvcMatcherBuilder.pattern(HttpMethod.GET, "/login/**"),
                                mvcMatcherBuilder.pattern(HttpMethod.GET, "/health/**"),
                                mvcMatcherBuilder.pattern(HttpMethod.GET, "/actuator/**"),
                                mvcMatcherBuilder.pattern(HttpMethod.GET, "/swagger-ui/**"),
                                mvcMatcherBuilder.pattern(HttpMethod.GET, "/swagger-ui.html**"),
                                mvcMatcherBuilder.pattern(HttpMethod.GET, "/v3/api-docs/**"),
                                mvcMatcherBuilder.pattern(HttpMethod.GET, "/v3/api-docs"))
                        .permitAll()
                        .anyRequest().authenticated())
                .formLogin(loginConfigurer -> loginConfigurer
                        .loginPage("/login")
                        .permitAll())
                .logout(logoutConfigurer -> logoutConfigurer
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll());

        return http.build();
    }
}
