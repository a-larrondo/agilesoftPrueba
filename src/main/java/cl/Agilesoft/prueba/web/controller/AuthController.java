package cl.Agilesoft.prueba.web.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.Agilesoft.prueba.persistence.model.Task;
import cl.Agilesoft.prueba.persistence.model.User;
import cl.Agilesoft.prueba.persistence.service.UserService;
import cl.Agilesoft.prueba.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<String> validateUser(@RequestBody User user) {

		String username = user.getUsername();
		String password = user.getPassword();

		if (username == null || password == null) {
			return new ResponseEntity<>("Debe ingresar usuario y clave", HttpStatus.BAD_REQUEST);
		}

		if (userService.validateUser(username, password)) {

			String jwt = jwtUtil.generateToken(user.getUsername());
			return new ResponseEntity<>(jwt, HttpStatus.OK);

		} else {
			return new ResponseEntity<>("Informacion del usuario invalida ", HttpStatus.UNAUTHORIZED);
		}

	}

}
