package com.app.user.services.impl.permisos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.user.models.permisos.Phone;
import com.app.user.repository.permisos.ITypeOfIdentificationRepository;
import com.app.user.services.permisos.ITypeOfIdentificationService;


/**
 * TypeOfIdentificationServiceImpl.java Clase que contiene los servicios
 * relacionados con los tipos de identificacion registrados, le implementamos la
 * anotacion @Autowired para inyectar la clase repository en este caso inyectamos la
 * interfaz que es quien contiene el repository con sus funciones necesaria por defecto
 * del JpaRepository
 * 
 * @author Vianey Vargas Morales
 * @version 1.0
 *
 */

@Service
public class TypeOfIdentificationServiceImpl implements ITypeOfIdentificationService {

	@Autowired
	private ITypeOfIdentificationRepository repository;
	/**
	 * función encargada para paginar la cantidad de registros.
	 *
	 * @param pageable parametro referencia para paginación
	 * @return retorna el paginado.
	 */
	@Override
	// @Cacheable
	public Page<Phone> listPageable(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public byte[] generarReporte() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * funcion encargada de guardar registros en la base de datos
	 *
	 * @param entity modelo entidad de los Tipos de documento
	 * @return retorna los datos registrados si el proceso fue exitoso.
	 */
	@Override
	@Transactional
	public Phone register(Phone entity) {
		return repository.save(entity);
	}


	/**
	 * funcion encargada de modificar un registro en la base de datos.
	 *
	 * @param entity modelo de la referencia para modificar el registro.
	 */
	@Override
	@Transactional
	public void update(Phone entity) {
		this.repository.save(entity);
	}

	/**
	 * funcion encargada para eliminar un registro de la base de datos.
	 *
	 * @param id parámetro por el cual se desea eliminar dicho registro.
	 */
	@Override
	@Transactional
	public void delete(String id) {
		this.repository.deleteById(id);
	}

	/**
	 * funcion encargada de Buscar un Tipo de documento especifico en la base de
	 * datos
	 *
	 * @param id parametro por el que se busca en los tipos de documento
	 *           registrados.
	 * @return retorna un tipo de documento si el proceso fue exitoso.
	 */
	@Transactional(readOnly = true)
	@Override
	public Phone findById(String id) {
		return repository.findById(id).orElse(null);
	}

	/**
	 * function encargada de retornar todos los tipos de documento registrados
	 *
	 * @return retorna una lista de los tipos de documentos almecenados
	 */
	@Transactional(readOnly = true)
	// @Cacheable
	@Override
	public List<Phone> getAll() {
		return repository.findAll();
	}

	/**
	 * funcion encargada de contar los registros
	 *
	 * @return
	 */
	@Transactional(readOnly = true)
	// @Cacheable
	@Override
	public long count() {
		return repository.count();
	}

}
