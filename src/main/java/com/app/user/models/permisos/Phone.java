package com.app.user.models.permisos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.app.user.models.Auditable;
import com.app.user.models.additional.information.DataState;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Vianey Vargas Morales
 * @version 1.0
 *
 */
	
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phone")
public class Phone extends Auditable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;

	@NotEmpty(message = "El celular no puede estar vacío")
	@Column(name = "celular", length = 20, nullable = false)
	private String numberPhone;
	
	@NotEmpty(message = "El código de la Ciudad no puede estar vacío")
	@Column(name = "city_code", nullable = false, length = 6, unique = true)
	private String cityCode;

	@NotEmpty(message = "El código del país no puede estar vacío")
	@Column(name = "codigo_pais", nullable = false, length = 6)
	private String countryCode;
	
	@JsonIgnore
	@NotNull(message = "El Usuario no puede estar vacío")
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_phone_usuarios_id"))
	private Usuario user;

	@Embedded
	private DataState state;

}
