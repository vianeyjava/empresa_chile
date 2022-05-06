package com.app.user.controllers.permisos;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.user.models.exception.ModelNotFoundException;
import com.app.user.models.permisos.Phone;
import com.app.user.services.permisos.ITypeOfIdentificationService;


@RestController
@RequestMapping(value = { "/tipoidentificacion" })
public class TypeOfIdentificationController {

	@Autowired(required = false)
    private ITypeOfIdentificationService service;

	//@Secured({"ROLE_ADMIN"})
	@PostMapping(value = { "/register" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> register(@Valid @RequestBody Phone entity) {
		service.register(entity);
		URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(entity.getId()).toUri();
		return ResponseEntity.created(url).build();
	}

	@Secured({"ROLE_ADMIN"})
	@PutMapping(value = { "/update/{id}"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> update(@Valid @RequestBody Phone entity,
			@PathVariable("id") String id) {
		service.update(entity);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	//@Secured({"ROLE_USER" ,"ROLE_ADMIN"})
	@GetMapping(value = { "/getAll" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Phone>> getAll() {
		List<Phone> list = new ArrayList<>();
		list = service.getAll();
		return new ResponseEntity<List<Phone>>(list, HttpStatus.OK);
	}

	@Secured({"ROLE_USER" ,"ROLE_ADMIN"})
	@GetMapping(value = "/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Phone> findById(@PathVariable("id") String id) {
		Phone p = new Phone();
		p = service.findById(id);
		if (p == null) {
			throw new ModelNotFoundException("No se encuentra el Id : " + id + " en la base de datos");
		}
		return new ResponseEntity<Phone>(p, HttpStatus.OK);
	}

	@Secured({"ROLE_ADMIN"})
	@DeleteMapping(value = { "/delete/{id}" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public void eliminar(@PathVariable("id") String id) {
		Phone entity = service.findById(id);
		if (entity == null) {
			throw new ModelNotFoundException("El Id: " + id + " no existe en la base de datos para eliminar.");
		} else {
			this.service.delete(id);
		}
	}

	@Secured({"ROLE_USER" ,"ROLE_ADMIN"})
	@GetMapping(value = { "/count" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public long count() {
		return service.count();
	}

}
