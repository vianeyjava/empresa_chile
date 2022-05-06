package com.app.user.services.impl.permisos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.user.models.permisos.Phone;
import com.app.user.repository.IPhoneRepository;
import com.app.user.services.permisos.IPhoneService;

@Service
public class PhoneServiceImpl implements IPhoneService {

	@Autowired
	private IPhoneRepository repository;
	
	@Transactional
	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Transactional
	@Override
	public Phone save(Phone entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public Phone findById(String id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@Override
	public List<Phone> registerAllPhone(List<Phone> list) {
		// TODO Auto-generated method stub
		return repository.saveAll(list);
	}

}
