package com.app.user.repository.permisos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.user.models.permisos.Usuario;


@Repository
public interface ILoginRepository extends JpaRepository<Usuario, String> {

	@Query("FROM Usuario us where us.email =:param_email")
	Usuario verificarNombreUsuario(@Param("param_email") String usuario) throws Exception;
	

//	@Transactional
//	@Modifying
//	@Query("UPDATE Users us SET us.password = :clave WHERE us.email = :paramCorreo")
//	void cambiarClave(@Param("clave") String clave, @Param("paramCorreo") String nombre) throws Exception;

}
