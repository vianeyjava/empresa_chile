package com.app.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.user.models.permisos.Usuario;


@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, String>{
	
	//public Usuario findByUsername(String username);
	
	public Usuario findByEmail(String email);
	
	@Query("select u from Usuario u where u.email=?1")
	public Usuario findByUsername2(String username);
	
	@Query(value="SELECT u.* FROM usuarios u\r\n"
			+ "INNER JOIN usuarios_roles ur\r\n"
			+ "ON ur.usuario_id = u.id\r\n"
			+ "inner JOIN roles r\r\n"
			+ "ON ur.role_id = r.id \r\n"
			+ "WHERE u.email=:email AND r.nombre=:role",nativeQuery = true)
	public Usuario findByEmailAndRole(String email, String role);
	
	
}
