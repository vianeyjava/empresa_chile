package com.app.user.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.user.models.permisos.Phone;
import com.app.user.services.permisos.IPhoneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * PhoneController.java clase que contiene las funciones necesarias para dar
 * respuesta a una peticion.
 *
 * @author Vianey Vargas Morales
 * @version 1.0
 */

@Api(value = "PhoneController")
@RestController
@RequestMapping(value = {"/phone" })
public class PhoneController {

	@Autowired
	private IPhoneService service;
	
	@ApiOperation(httpMethod = "POST", value = "save phone")
	@ApiResponses({ 
		    @ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "Unauthenticated or unauthorized user"),
			@ApiResponse(code = 403, message = "forbidden!!!"), 
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping(value = { "/save" }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> save(@Valid @RequestBody Phone entity,BindingResult result)
			throws NoSuchAlgorithmException, NoSuchProviderException {
		Phone user = null;
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			user = service.save(entity);
		} catch (DataAccessException ex) {
			response.put("mensaje", "Ha ocurrido un error, inténtalo nuevamente.");
			response.put("error", ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Teléfono registrado con éxito.");
		response.put("entityNewPhone", user);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	
	
}
