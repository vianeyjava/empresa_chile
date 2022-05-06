package com.app.user.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.user.dto.MyApiResponse;
import com.app.user.models.permisos.Usuario;
import com.app.user.request.PhoneRequest;
import com.app.user.services.permisos.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * UserController.java clase que contiene las funciones necesarias para dar
 * respuesta a una peticion.
 *
 * @author Vianey Vargas Morales
 * @version 1.0
 */

@Api(value = "UserController")
@RestController
@RequestMapping(value = {"/user" })
public class UserController {

	private static String badRequestMessage = "Solicitud incorrecta";
	
	/* Clase propia de Spring Oauth ConsumerTokenServices para el manejo tokens, esta clase viene siendo 
	 * como un servicio para hacer operaciones con el token*/
	@Resource(name = "tokenServices")
	private ConsumerTokenServices tokenServices;
	
	@Autowired
	private IUserService service;
	
	@ApiOperation(httpMethod = "POST", value = "save user")
	@ApiResponses({ 
		    @ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "Unauthenticated or unauthorized user"),
			@ApiResponse(code = 403, message = "forbidden!!!"), 
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 500, message = "Internal server error") })
	@PostMapping(value = { "/save" })
	public ResponseEntity<?> save(@RequestBody @Valid Usuario entity, PhoneRequest phoneRequest,BindingResult result)
			throws NoSuchAlgorithmException, NoSuchProviderException {
		System.out.println("RequestPhone===>"+phoneRequest);
		Usuario user = null;
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		Usuario u = service.findByUsername2(entity.getEmail());
		
		if (u !=null) {
			return new ResponseEntity<>(
					MyApiResponse.builder().code(HttpStatus.BAD_REQUEST.value()).message(badRequestMessage)
							.content("El Email: "+u.getEmail()+" ya se encuentra registrado en la base de datos! ").build(),
					HttpStatus.BAD_REQUEST);
		}
		try {
			user = service.saveUser(entity, phoneRequest);
		} catch (DataAccessException ex) {
			response.put("error", HttpStatus.INTERNAL_SERVER_ERROR);
			response.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("message", "Ha ocurrido un error, inténtalo nuevamente.");
			//response.put("error", ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("statusCode", HttpStatus.OK.value());
		response.put("message", "Usuario registrado con éxito.");
		response.put("entityNewUser", user);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN"})
	@GetMapping(value = { "/findAll" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Usuario>> getAll() {
		List<Usuario> list = new ArrayList<>();
		list = service.findAll();
		return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
	}
	
	/*
	 * Colocamos con puntos /anular/{tokenId:.* para que en la web capture los
	 * puntos . que genera el token
	 */
	/*@GetMapping(value = "/anular/{tokenId:.*}")
	public void revokeToken(@PathVariable("tokenId") String token) {
		tokenServices.revokeToken(token);				
	}*/
	@GetMapping(value = "/anular/{tokenId:.*}")
	public void revokeToken(@PathVariable("tokenId") String token) {
		tokenServices.revokeToken(token);				
	}

}
