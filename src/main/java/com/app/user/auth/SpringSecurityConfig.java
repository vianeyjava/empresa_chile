package com.app.user.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.app.user.config.event.AuthenticationSuccessErrorHandler;

//@EnableGlobalMethodSecurity(securedEnabled=true)
//@Configuration
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${security.signing-key}")
	private String signingKey;

	@Value("${security.encoding-strength}")
	private Integer encodingStrength;

	@Value("${security.security-realm}")
	private String securityRealm;
	
	/*
	 * Creamos un objeto de tipo Datasource llamado dataSource y le inyectamos
	 * con @Autowired este objeto hace referencia al archivo application.properties
	 */
	@Autowired
	private DataSource dataSource;
	

	@Autowired
	private UserDetailsService usuarioService;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	/**
	 * Para registrar Evento de login si fue exitoso o NO.
	 */
	@Autowired
	private AuthenticationSuccessErrorHandler eventPublisher;
	
	/*@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.usuarioService).passwordEncoder(/*passwordEncoder()*/bcrypt)
		.and().authenticationEventPublisher(eventPublisher);
	}

	/*@Bean("authenticationManager")
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}*/
	@Bean("authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	      return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().httpBasic()
				.realmName(securityRealm).and().csrf().disable();
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(signingKey);
		//jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVADA_SERVER);
		//jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLICA_SERVER);
		return converter;
	}

	@Bean
	public TokenStore tokenStore() {
		// return new JwtTokenStore(accessTokenConverter());//Para guardar en memoria
		return new JdbcTokenStore(this.dataSource);// para guardar token en base de datos
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		defaultTokenServices.setReuseRefreshToken(false);
		return defaultTokenServices;
	}
	
	  /*@Override public void configure(HttpSecurity http) throws Exception {
	  http.authorizeRequests() .anyRequest().authenticated() .and()
	  .csrf().disable()
	  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	  }*/
	 

	

}
