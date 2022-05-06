package com.app.user.services.impl.permisos;

import org.springframework.transaction.annotation.Transactional;

import com.app.user.models.permisos.Role;
import com.app.user.repository.permisos.IRoleRepository;
import com.app.user.services.permisos.IRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleRepository repository;
	
	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public Role register(Role entity) {
		// TODO Auto-generated method stub
		String name=entity.getName().toUpperCase();
		entity.setName(name);
		return repository.save(entity);
	}

	@Override
	public Role findById(String id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@Override
	public Role findByName(String name) {
		// TODO Auto-generated method stub
		return repository.findByName(name);
	}

	@Override
	public void setRoles(String user_id, String role_id) {
		// TODO Auto-generated method stub
		repository.setRoles(user_id, role_id);
	}

}
