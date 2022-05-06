package com.app.user.services.permisos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.user.models.permisos.Phone;


public interface ITypeOfIdentificationService {

	Page<Phone> listPageable(Pageable pageable);

	public byte[] generarReporte();

	public Phone register(Phone entity);

	public void update(Phone entity);

	public void delete(String id);

	public Phone findById(String id);

	public List<Phone> getAll();

	public long count();
}
