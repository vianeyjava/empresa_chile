package com.app.user.repository.permisos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.user.models.permisos.Role;


@Repository
public interface IRoleRepository  extends JpaRepository<Role, String>{

	@Modifying
	@Transactional
	@Query(value="insert into usuarios_roles(usuario_id, role_id) values(:user_id,:role_id);", nativeQuery = true)
	public void setRoles(String user_id, String role_id);
	
	public Role findByName(String name);
}
