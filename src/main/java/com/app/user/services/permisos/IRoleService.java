package com.app.user.services.permisos;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import com.app.user.models.permisos.Role;


public interface IRoleService {

	/**
	 * Elimina definitivamente el registro
	 * 
	 * @param id Identificador del registro
	 * @version 1.0
	 * @author Vianey Vargas Morales
	 */
	public void delete(String id);

	/**
	 * Guarda los datos del usuario
	 * 
	 * @author Vianey Vargas Morales
	 * @param entity
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	public Role register(Role entity);

	/**
	 * Método para buscar rol por id
	 * 
	 * @author Vianey Vargas Morales
	 * @param id
	 * @return
	 */
	public Role findById(String id);

	/**
	 * Método para buscar rol po nombre
	 * 
	 * @author Vianey Vargas Morales
	 * @param name
	 * @return
	 */
	public Role findByName(String name);

	/**
	 * Método para setear o asignar el usuario y el rol a un registro de terminado
	 * 
	 * @author Vianey Vargas Morales
	 * @param user_id
	 * @param role_id
	 */
	public void setRoles(String user_id, String role_id);

}
