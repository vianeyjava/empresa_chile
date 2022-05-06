package com.app.user.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.app.user.config.AuthException;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Autowired
    private ResourceServerTokenServices tokenServices;
	
    @Value("${security.jwt.resource-ids}")
    private String resourceIds;
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceIds).tokenServices(tokenServices);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
                http
                .exceptionHandling().authenticationEntryPoint(new AuthException())
                .and()
                .requestMatchers()
                .and()
                .authorizeRequests()                          
                .antMatchers(HttpMethod.GET, "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/clientes", "/api/clientes/page/**", "/api/uploads/img/**", "/images/**").permitAll()
                .antMatchers(HttpMethod.POST, "/user/save/**").permitAll()//QUITAR
                .antMatchers(HttpMethod.GET, "/user/findAll/**").authenticated()//QUITAR
        		.antMatchers(HttpMethod.POST, "/role/register/**").permitAll()//QUITAR
        		.antMatchers(HttpMethod.POST, "/phone/save/**").authenticated();
    }
    
	/*@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/swagger-ui",
                "/webjars/**").permitAll()
		.antMatchers(HttpMethod.GET, "/api/clientes", "/api/clientes/page/**", "/api/uploads/img/**", "/images/**").permitAll()
		.antMatchers(HttpMethod.POST,"/notification/fcm/**").permitAll()
		.antMatchers(HttpMethod.POST, "/user/register/**").permitAll()//QUITAR
		.antMatchers(HttpMethod.POST, "/role/register/**").permitAll()//QUITAR
		.antMatchers(HttpMethod.POST,"/regemail/register/**").permitAll()
		.antMatchers(HttpMethod.POST,"/regemail/register/**").permitAll()
		.antMatchers(HttpMethod.POST, "/businessType/register/**").permitAll()
		.antMatchers(HttpMethod.GET, "/businessType/findAll/**").permitAll()
		.antMatchers(HttpMethod.POST, "/business/register/**").permitAll()
		.antMatchers(HttpMethod.POST, "/tipoidentificacion/register/**").permitAll()
		.antMatchers(HttpMethod.GET, "/tipoidentificacion/getAll/**").permitAll()
		.antMatchers(HttpMethod.GET, "/hotel/findAll/**").permitAll()
		.antMatchers(HttpMethod.GET, "/category/findAll/**").permitAll()
		.antMatchers(HttpMethod.GET,"/carrusel/findAll/**").permitAll()
		.antMatchers(HttpMethod.GET,"/hotelImage/findByHotelId/**").permitAll()
		.anyRequest().authenticated().and().cors().configurationSource(corsConfigurationSource());
	}*/
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PATCH", "PUT","OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList(
				"Authorization","Content-Type","Access-Control-Allow-Headers", "Access-Control-Allow-Origin",
				"Access-Control-Request-Method", "Access-Control-Request-Headers", "Origin", "Cache-Control"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(
				new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

	
}
