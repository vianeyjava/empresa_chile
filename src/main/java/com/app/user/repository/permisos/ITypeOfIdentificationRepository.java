package com.app.user.repository.permisos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.user.models.permisos.Phone;


/**
 * ITypeOfIdentificationDAO.java interfaz que implementa la persistencia de
 * datos
 * 
 * @author vianeyvargasmorales
 * @version 1.0
 *
 */
@Repository
public interface ITypeOfIdentificationRepository extends JpaRepository<Phone, String> {

	
}
