package com.app.user.services.permisos;

import com.app.user.models.permisos.ResetToken;

public interface IResetTokenService{

	public ResetToken findByToken(String token);
	
	public void register(ResetToken token);
	
	public void delete(ResetToken token);

}
