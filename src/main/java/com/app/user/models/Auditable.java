package com.app.user.models;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

	/**
	 * Campo para guardar el Usuario Creado por
	 * @author vianeyvargasmorales
	 */
	@CreatedBy
	protected String createdBy;

	/**
	 * Campo para guardar la fecha y hora de creación
	 * @author vianeyvargasmorales
	 */
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createdDate;

	/**
	 * Campo para guardar la Ultima actualización por
	 * @author vianeyvargasmorales
	 */
	@LastModifiedBy
	protected String lastModifiedBy;

	/**
	 * Campo para guardar la fecha y hora de ultima actualización
	 * @author vianeyvargasmorales
	 */
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastModifiedDate;

	// getters and setter here
}
