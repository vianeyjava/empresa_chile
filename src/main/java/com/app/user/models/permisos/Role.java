package com.app.user.models.permisos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.GenericGenerator;

import com.app.user.models.Auditable;

import lombok.Data;

@Data
@Entity
@Table(name="roles")
public class Role extends Auditable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;
	
	@NotEmpty(message = "El nombre del Rol no puede estar vacío")
	@Column(name = "nombre", unique = true, nullable = false, length=20)
	private String name;

	@Column(name = "descripcion")
	private String description;
}
