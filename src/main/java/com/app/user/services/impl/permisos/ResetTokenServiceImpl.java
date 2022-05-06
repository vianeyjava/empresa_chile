package com.app.user.services.impl.permisos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.user.models.permisos.ResetToken;
import com.app.user.repository.permisos.IResetTokenRepository;
import com.app.user.services.permisos.IResetTokenService;


@Service
public class ResetTokenServiceImpl implements IResetTokenService {

	@Autowired
	private IResetTokenRepository dao;

	@Override
	public ResetToken findByToken(String token) {
		return dao.findByToken(token);
	}

	@Override
	@Transactional
	public void register(ResetToken token) {
		dao.save(token);

	}

	@Override
	@Transactional
	public void delete(ResetToken token) { 
		dao.delete(token);
	}

}
