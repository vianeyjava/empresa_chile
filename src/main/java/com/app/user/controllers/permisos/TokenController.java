package com.app.user.controllers.permisos;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.user.services.impl.UploadFileServiceImpl;

@RestController
@RequestMapping(value = { "/tokens" })
public class TokenController {

	private final Logger logger = LoggerFactory.getLogger(TokenController.class);
	
	@Autowired
	private ConsumerTokenServices tokenServices;
	
	/**
	 * Se coloca esta expresion :.* porque el token viene con puntos
	 * @author Vianey Vargas Morales
	 * @param token
	 */
	@GetMapping(value = { "/anular/{tokenId:.*}" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public void revocarToken(@PathVariable("tokenId") String token) {
		tokenServices.revokeToken(token);
	}
	
	/**
	 * También puedo utilizar este para anular el token.
	 * Se coloca esta expresion :.* porque el token viene con puntos
	 * @author Vianey Vargas Morales
	 * @param tokenid
	 * @return
	 */
	@PostMapping(value = { "/logout/{tokenId:.*}" }, produces = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<?> logout(@PathVariable("tokenId") String tokenid) {
		logger.debug("Invalidating token {}", tokenid);
		Map<String, Object> response = new HashMap<>();
		try {
			tokenServices.revokeToken(tokenid);
		} catch (DataAccessException e) {
			response.put("mensaje", "Ha ocurrido un error, intentelo nuevamente.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    response.put("tokenId", tokenid);
	    response.put("mensaje", "El Token ha sido revocado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	  }

}
