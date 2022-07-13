package com.perficient.pbcpauthorizationservice.config;


import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.perficient.pbcpauthorizationservice.util.Jwks;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.util.UUID;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * Implements OAuth2 security configuration.
 *
 * @author tyler.barton
 * @version 1.0, 6/30/2022
 * @project PBCP-AuthorizationService
 * @hidden https://docs.spring.io/spring-authorization-server/docs/current/reference/html/getting-started.html
 */
@Configuration(proxyBeanMethods = false)
@Import(OAuth2AuthorizationServerConfiguration.class) // From spring-security-oauth2-authorization-server
public class SecurityConfig {

    /**
     * A Spring Security filter chain for the protocol endpoints.
     *
     * @param http the HTTP configuration
     * @return the filter chain
     * @throws Exception if an error occurs
     */
    @Bean
    @Order(HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        // @formatter:off
        http
                .exceptionHandling(exceptions ->
                        exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                );
        // @formatter:on
        return http.build();
    }

    /**
     * A Spring Security filter chain for authentication.
     */
    @Bean
    @Order(2) // Order is important here. The order of the filters is important. The first filter in the chain is the one that is executed first.
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("pbcp-client")
                .clientSecret("pbcp-client-secret")
                // GrantType allows for the client to request access tokens for the given scopes.
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                // RedirectUri is the URL that the client will be redirected to after the user logs in.
                .redirectUri("http://localhost:8080/login/oauth2/code/pbcp-client")
                .redirectUri("http://localhost:8080/authorized")
                // Scopes are used to grant access to the client.
                .scope(OidcScopes.OPENID)
                .scope("message.read")
                .scope("message.write")
                // ClientSettings allows for the client to be configured.
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        // Save the client to the database as if in-memory client registration is not enabled.
        JdbcRegisteredClientRepository repository = new JdbcRegisteredClientRepository(jdbcTemplate);
        repository.save(registeredClient);

        return repository;
    }

    /**
     *
     * @param jdbcTemplate the JDBC template
     * @param registeredClientRepository the registered client repository
     * @return
     */
    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
    }

    /**
     * Provides the consent service. This service is used to store the user's consent.
     * @param jdbcTemplate the JDBC template
     * @param registeredClientRepository the registered client repository
     * @return the consent service
     */
    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }

    /**
     * JSON Web Key Source to retrieve the public key.
     * @return the JWK set
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return ((jwkSelector, securityContext) -> jwkSelector.select(jwkSet));
    }

    /**
     * Registers the provider (ie: Google, Azure, Facebook, GitHub, etc.) with the authorization service.
     * @return
     */
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder().issuer("http://localhost:9000").build();
    }

    /**
     * Creates an in-memory database.
     * @return the embedded database
     */
    public EmbeddedDatabase embeddedDatabase() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql")
                .addScript("org/springframework/security/oauth2/server/authorization/oauth2-authorization-consent-schema.sql")
                .addScript("org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql")
                .build();
    }
}
