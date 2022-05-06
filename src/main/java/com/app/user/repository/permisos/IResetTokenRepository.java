package com.app.user.repository.permisos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.user.models.permisos.ResetToken;

@Repository
public interface IResetTokenRepository extends JpaRepository<ResetToken, String>{
	
	//from token where token = :? 
	public ResetToken findByToken(String token);

}
