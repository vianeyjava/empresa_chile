package com.app.user.services.impl.permisos;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.user.models.additional.information.DataState;
import com.app.user.models.exception.ModelNotFoundException;
import com.app.user.models.permisos.Phone;
import com.app.user.models.permisos.Role;
import com.app.user.models.permisos.Usuario;
import com.app.user.repository.IPhoneRepository;
import com.app.user.repository.IUsuarioRepository;
import com.app.user.request.PhoneRequest;
import com.app.user.services.permisos.IPhoneService;
import com.app.user.services.permisos.IRoleService;
import com.app.user.services.permisos.IUserService;
import com.app.user.util.Utils;


@Service
public class UsuarioServiceImpl implements IUserService, UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	@Autowired
	private IUsuarioRepository repository;


	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IPhoneService servicePhone;
	
	@Autowired
	private IPhoneRepository repositoryPhone;
	

	/**
	 * función encargada para buscar un determinado usuario con sus Roles de acceso,
	 * ó permisos a la aplicación.
	 *
	 * @param username parametro referencia del Usuario a buscar
	 * @return userDetails retorna el usuario con sus Roles determinados en la base
	 *         de datos.
	 */
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = repository.findByUsername2(username);
		System.out.println(user);

		if (user == null) {
			logger.error("Error en el login: no existe el usuario '" + username + "' en el sistema!");
			throw new UsernameNotFoundException(
					String.format("Error en el login: no existe el usuario '" + username + "' en el sistema!"));
		}

		List<GrantedAuthority> authorities = user.getRoles().stream()
				// .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.peek(autority -> logger.info("Role: " + autority.getAuthority())).collect(Collectors.toList());

		return new User(user.getEmail(), user.getPassword(),
				user.getState().isActive() && (user.getState().isEnable()), true, true, true, authorities);
	}

	@Transactional
	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	@Transactional
	@Override
	public Usuario register(Usuario entity, Role _role) {
		DataState dataState = new DataState();
		dataState.setEnable(true);//Para acceder a Login debe estar activado por email
		dataState.setActive(true);
		String normalPassword = entity.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		entity.setPassword(encoder.encode(normalPassword));
		entity.setState(dataState);
		
		Usuario _user = repository.save(entity);
		
		return _user;
	}

	@Transactional(readOnly = true)
	@Override
	public Usuario findById(String id) {
		// TODO Auto-generated method stub
		if (!repository.existsById(id)) {
			throw new ModelNotFoundException(
					"El Usuario con el ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
		}
		return repository.findById(id).orElse(null);
	}


	@Override
	public Usuario register(Usuario entity) throws NoSuchAlgorithmException, NoSuchProviderException {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public Usuario findByEmailAndRole(String email, String role) {
		// TODO Auto-generated method stub
		return repository.findByEmailAndRole(email, role);
	}

	@Transactional
	@Override
	public Usuario saveUser(Usuario entity,PhoneRequest entityRequest) {
		Usuario userResult =null;
	 	String normalPassword = entity.getPassword();
		System.out.println("PASSWORD NORMAL");
		System.out.println(normalPassword);
		// Preparamos el estado que nos permitira saber si el usuario no ha confirmado
		// su registro
		DataState data = DataState.builder().active(true).enable(true).build();
		// Preparamos el role que se asignara en este registro { ROLE_BUSINESS }
		Role _role = roleService.findByName("ROLE_USER");
		// Preparamos el nuevo usuario
		//Usuario user = new Usuario();
		// Asignamos todos los datos recibidos
		// Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(entity.getEmail());
        if (mather.find() == true) {
        	entity.setEmail(entity.getEmail());
            System.out.println("El email ingresado es válido.");
        } else {
            System.out.println("El email ingresado es inválido.");
        }
		//entity.setEmail(entity.getEmail());
		entity.setPassword(normalPassword);
		entity.setFirstName(entity.getFirstName());
		entity.setLastName(entity.getLastName());
		entity.setState(data);
		DataState dataStatePhone = new DataState();
		dataStatePhone.setActive(true);
		dataStatePhone.setEnable(true);
		//Registramos la lista de telefonos
		entity.getListPhone().forEach(element ->{
			element.setUser(entity);
			element.setNumberPhone(element.getNumberPhone());
			element.setCityCode(element.getCityCode());
			element.setCountryCode(element.getCountryCode());
			element.setState(dataStatePhone);
		});
		userResult = repository.save(entity);

		// Asignamos el rol { ROLE_USUARIO } al nuevo usuario
		roleService.setRoles(userResult.getId(), _role.getId());
		
		// Registramos el usuario junto con el Rol
		userResult = this.register(entity, _role);
		
		return userResult;
	}

	/*@Override
	public Usuario findByUsername(String username) {
		// TODO Auto-generated method stub
		return repository.findByUsername(username);
	}*/


	@Override
	public Usuario findByUsername2(String username) {
		// TODO Auto-generated method stub
		return repository.findByUsername2(username);
	}

	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
