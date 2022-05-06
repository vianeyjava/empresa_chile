package com.app.user.controllers.permisos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.user.models.permisos.ResetToken;
import com.app.user.services.permisos.ILoginService;
import com.app.user.services.permisos.IResetTokenService;


@RestController
@RequestMapping(value = { "/login", "/recuperar" })
public class LoginController {
	
	private static String errorMessage = "Ha ocurrido un error, inténtalo nuevamente";
	private static String successMessage = "Operación exitosa";
	private static String badRequestMessage = "Solicitud incorrecta";	

	@Autowired
	private ILoginService service;

	@Autowired
	private IResetTokenService tokenService;

	
	@GetMapping(value = { "/restablecer/verificar/{token}" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> restablecerClave(@PathVariable("token") String token) {
		int rpta = 0;
		try {
			if (token != null && !token.isEmpty()) {
				ResetToken rt = tokenService.findByToken(token);
				String iddRt = rt.getId();
				if (rt != null && iddRt != null && !iddRt.isBlank() && !iddRt.isEmpty()) {
					if (!rt.isExpirado()) {
						rpta = 1;
					}
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<Integer>(rpta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
	}
	
}
