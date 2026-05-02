package pm.inlivefilemanager.config;

import lombok.extern.slf4j.Slf4j;
import pm.inlivefilemanager.config.keycloak.KeycloakGrantedAuthoritiesConverter;
import pm.inlivefilemanager.config.keycloak.ReactiveKeycloakJwtAuthenticationConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

@Slf4j
@EnableWebFluxSecurity
@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {
    @Value("${application.client-id}")
    private String clientId;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)

                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(Customizer.withDefaults())
                );

        http
                .authorizeExchange(authorizeExchangeSpec -> {
                    authorizeExchangeSpec
                            .pathMatchers("/user-photos/upload/**").hasAnyAuthority("CLIENT", "ADMIN", "SUPER_MANAGER")
                            .pathMatchers("/user-photos/remove/**").hasAnyAuthority("CLIENT", "ADMIN", "SUPER_MANAGER")
                            .pathMatchers("/*/remove/**").hasAnyAuthority("ADMIN", "SUPER_MANAGER")
                            .pathMatchers("/*/upload/**").hasAnyAuthority("ADMIN", "SUPER_MANAGER")
                            .anyExchange()
                            .permitAll();
                });


        http.oauth2ResourceServer(authorizeExchangeSpec -> {
            authorizeExchangeSpec.jwt(jwtSpec -> {
                jwtSpec.jwtAuthenticationConverter(jwtAuthenticationConverter);
            });
        });



        return http.build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

    @Bean
    public Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverterForKeycloak() {
        return new ReactiveKeycloakJwtAuthenticationConverter(
                new KeycloakGrantedAuthoritiesConverter(clientId)
        );
    }
}
