package com.app.user.response;

import java.util.List;

import com.app.user.models.permisos.Phone;
import com.app.user.models.permisos.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ResponseUser.java Clase respuesta de los favoritos.
 * 
 * @author Vianey Vargas Morales
 * @version 1.0
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseUser {

	private String id;

	private Usuario user;

	private List<Phone> listFavorite;

}
