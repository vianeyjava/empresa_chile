package com.app.user.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.user.models.permisos.Role;
import com.app.user.services.permisos.IRoleService;


/**
 * RoleController.java clase que contiene las funciones necesarias para dar
 * respuesta a una peticion.
 *
 * @author Vianey Vargas Morales
 * @version 1.0
 */

@RestController
@RequestMapping(value = {"/role" })
public class RoleController {

	@Autowired
	private IRoleService service;

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(value = { "/register" })
	public ResponseEntity<?> register(@Valid @RequestBody Role entity, BindingResult result) {
		Role role = null;
		Map<String, Object> response = new HashMap<>();
		/* Trabajando Con Java 8 y programacion funcional */
		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			role = service.register(entity);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Ha ocurrido un error, inténtalo nuevamente.");
			response.put("error", ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El Rol ha sido registrado con éxito.");
		response.put("entityRole", role);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	//@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping(value = { "/listar/{id}" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> show(@PathVariable String id) {
		
		Role rol = service.findById(id);
		
		/*if(usuario == null) {
			throw new ModeloNotFoundException("El Usuario con el ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
		}*/
		
		return new ResponseEntity<Role>(rol, HttpStatus.OK);
	}

}
