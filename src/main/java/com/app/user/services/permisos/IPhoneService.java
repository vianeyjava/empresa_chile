package com.app.user.services.permisos;

import java.util.List;

import com.app.user.models.permisos.Phone;

public interface IPhoneService {

	public void delete(String id);

	/**
	 * Registro de Phone
	 * @author Vianey Vargas Morales
	 * @param entity
	 * @param filePhoto
	 * @return
	 */
	public Phone save(Phone entity);


	public Phone findById(String id);
	
	/**
	 * Guarda una lista de registros Phone
	 * @param list
	 * @return
	 * @author Vianey Vargas Morales
	 * @version 1.0
	 */
	public List<Phone> registerAllPhone(List<Phone> list);

}
