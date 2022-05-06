package com.app.user.models.permisos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.app.user.models.Auditable;
import com.app.user.models.additional.information.DataState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario extends Auditable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;
	
	@Column(name = "clave", nullable = false)
	private String password;

	//private Boolean enabled;
	
	@ApiModelProperty(notes = "El nombre no puede estar vacío")
	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 5, max = 30, message = "El tamaño del nombre debe tener entre {min} á {max} caracteres")
	@Column(name = "nombres", nullable = false)
	private String firstName;
	
	// @NotEmpty(message ="El apellido no puede estar vacio")
	@Column(name = "apellidos", nullable = true, length = 70)
	private String lastName;
	
	@NotEmpty(message = "El email no puede estar vacío")
	@Email(message = "No es una dirección de correo bien formada")
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "usuario_id", "role_id" }) })
	private List<Role> roles;
	
	@OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Phone> listPhone;
	
	
	// @JsonFormat(pattern = "yyyy-MM-dd")
		// @DateTimeFormat(pattern = "yyyy-MM-dd")

		@JsonSerialize(using = ToStringSerializer.class)
		@Column(name = "fecha_registro", nullable = true)
		private LocalDateTime dateRegister;
	
	/**
     * @Embedded con esta anotacion, indicamos que el campo o la propiedad de
     * una entidad es una instancia de una clase que puede ser integrable. Es
     * decir, para que funcione, el campo que hayamos anotado como @Embedded,
     * debe corresponderse con una clase que tenga la anotacion @Embeddable.
     *
     */
	@Embedded
	private DataState state;
	
	@PrePersist
	public void prepersist() {
		// dateRegister new Date();
		dateRegister = LocalDateTime.now();
	}
	
}
