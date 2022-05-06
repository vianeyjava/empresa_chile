package com.app.user.auth;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter  {
	
	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String clientSecret;

	@Value("${security.jwt.grant-type}")
	private String grantType;

	@Value("${security.jwt.scope-read}")
	private String scopeRead;

	@Value("${security.jwt.scope-write}")
	private String scopeWrite = "write";

	@Value("${security.jwt.resource-ids}")
	private String resourceIds;
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService usuarioService;
	
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private InfoAdicionalToken infoAdicionalToken;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()");
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer.inMemory().withClient(clientId).secret(passwordEncoder.encode(clientSecret))
		.authorizedGrantTypes("password", "refresh_token")
		.authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials", "implicit")
		.scopes(scopeRead, scopeWrite).resourceIds(resourceIds).accessTokenValiditySeconds(86400)//24H
		.refreshTokenValiditySeconds(0);
	}
	
	/*@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		//INI-CAMBIO PARA SPRING BOOT 2 (secret(bcrypt.encode(clientSecret))
		configurer.inMemory().withClient(clientId).secret(passwordEncoder.encode(clientSecret)).authorizedGrantTypes(grantType)
				.scopes(scopeRead, scopeWrite).resourceIds(resourceIds).accessTokenValiditySeconds(1000)//10 segundos
				.refreshTokenValiditySeconds(0);
		//FIN-CAMBIO PARA SPRING BOOT 2
	}*/
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken,accessTokenConverter));
		endpoints.tokenStore(tokenStore)
		.accessTokenConverter(accessTokenConverter)
		.tokenEnhancer(enhancerChain)
		.authenticationManager(authenticationManager)
		.userDetailsService(usuarioService);
	}

	/*@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
		.withClient("app_turiscamp")
		.secret(passwordEncoder.encode("789Administrador"))
		.scopes("read", "write")
		.authorizedGrantTypes("password", "refresh_token")
		.authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials", "implicit")
		.accessTokenValiditySeconds(86400)
//		.refreshTokenValiditySeconds(86400);
		.and()
        .withClient("app-web")
        .secret(passwordEncoder.encode("789Administrador**"))
        .scopes("read", "write")
        .authorizedGrantTypes("password", "refresh_token")
		.authorizedGrantTypes("password", "authorization_code", "refresh_token", "client_credentials", "implicit")
		.accessTokenValiditySeconds(86400);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));
		
		endpoints.authenticationManager(authenticationManager)
		.userDetailsService(usuarioService)
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter())
		.tokenEnhancer(tokenEnhancerChain);
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVADA_SERVER);
		jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLICA_SERVER);
		return jwtAccessTokenConverter;
	}*/
}
