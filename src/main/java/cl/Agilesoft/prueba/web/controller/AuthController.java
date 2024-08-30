package cl.Agilesoft.prueba.web.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.Agilesoft.prueba.persistence.model.User;
import cl.Agilesoft.prueba.persistence.repository.UserRepo;
import cl.Agilesoft.prueba.persistence.service.UserService;
import cl.Agilesoft.prueba.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@PostMapping("/login")
	public ResponseEntity validateUser(@RequestBody Map<String, String> loginRequest) throws Exception {
		String username = loginRequest.get("username");
		String password = loginRequest.get("password");
		
		try {
			validarLogin(username, password);
			
		}catch(BadCredentialsException e) {
			throw new Exception("Informacion del usuario invalida", e);
		}
		
		String jwt = jwtUtil.generateToken(username);
		return ResponseEntity.ok(jwt);
		
	}
	
	private void validarLogin(String username, String password) {
		if(username == null || password == null) {
			throw new BadCredentialsException("Debe ingresar password y clave");
		}

		if(userService.validateUser(username, password)) {
			throw new BadCredentialsException("Login ha fracasado :(");
		}
		 
		
	}
}
