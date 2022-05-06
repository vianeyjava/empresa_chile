package com.app.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.user.models.permisos.Phone;


@Repository
public interface IPhoneRepository extends JpaRepository<Phone, String>{
	
}
