package com.app.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.app.user.audit.AuditorAwareImpl;


/**
 * Clase configuracion para habilitar la auditoria JPA 
 * @author Vianey Vargas Morales
 * @version 0.0.1
 * @see https://www.turiscam.com/
 *
 */
@Configuration
@EnableJpaAuditing
public class PersistenceConfig {

	@Bean
	public AuditorAware<String> auditorProvider() {
		return new AuditorAwareImpl();
	}
}
