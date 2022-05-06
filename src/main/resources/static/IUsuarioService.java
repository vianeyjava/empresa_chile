package com.app.turiscamp.models.services;

import com.app.turiscamp.models.entity.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);
}
