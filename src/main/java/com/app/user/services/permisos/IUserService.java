package com.app.user.services.permisos;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.user.models.permisos.Phone;
import com.app.user.models.permisos.Role;
import com.app.user.models.permisos.Usuario;
import com.app.user.request.PhoneRequest;

public interface IUserService {

	//public Usuario findByUsername2(String username);
	
	public Usuario findByUsername2(String username);

	/**
	 * Elimina definitivamente el registro
	 * 
	 * @param id Identificador del registro
	 * @version 1.0
	 * @author Vianey Vargas Morales
	 */
	public void delete(String id);

	/**
	 * Registro simple de usuario
	 * 
	 * @author Vianey Vargas Morales
	 * @param entity
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	public Usuario register(Usuario entity) throws NoSuchAlgorithmException, NoSuchProviderException;
	
	/**
	 * Registro de Usuario con foto
	 * @author Vianey Vargas Morales
	 * @param entity
	 * @param filePhoto
	 * @return
	 */
	public Usuario saveUser(Usuario entity,PhoneRequest entityRequest);
	/**
	 * Registro de usuario junto con el Rol
	 * 
	 * @author Vianey Vargas Morales
	 * @param entity
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	public Usuario register(Usuario entity, Role _role);


	public Usuario findById(String id);

	
	/**
	 * Obtiene un usuario de un email en un rol específico
	 * @param email
	 * @param role
	 * @return Datos de usuario
	 * @author Vianey Vargas Morales
	 */
	public Usuario findByEmailAndRole(String email,String role);

	/**
	 * Obtiene todos los usuarios registrados
	 * @author Vianey Vargas Morales
	 * @return
	 */
	public List<Usuario> findAll();
	
	

}
