package com.app.user.services.impl.permisos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.user.models.permisos.Usuario;
import com.app.user.repository.permisos.ILoginRepository;
import com.app.user.services.permisos.ILoginService;


@Service
public class LoginServiceImpl implements ILoginService{
	
	@Autowired
	private ILoginRepository dao;

//	@Autowired
//	private IUserService userService;
	
	
	@Override
	public Usuario verificarNombreUsuario(String usuario) {
		Usuario us = null;
		try {
			us = dao.verificarNombreUsuario(usuario);
			us = us != null ? us : new Usuario();
		} catch (Exception e) {
			us = new Usuario();
		}
		return us;
	}

}
