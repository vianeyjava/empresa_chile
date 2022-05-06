/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.models.additional.information;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DataState.java clase @Embeddable con esta anotacion indicamos que la clase
 * puede ser integrable dentro de una entidad.
 *
 * @author vianey
 * @version 1.0
 */
@Data
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataState implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = true)
	private boolean enable;
	@Column(nullable = true)
	private boolean active;

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
