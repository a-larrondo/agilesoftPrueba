package cl.Agilesoft.prueba.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.Agilesoft.prueba.persistence.model.User;
import cl.Agilesoft.prueba.persistence.service.UserService;
import cl.Agilesoft.prueba.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<String> createToken(@RequestBody User user) {

		String username = user.getUsername();
		String password = user.getPassword();

		if (username == null || password == null)
			return new ResponseEntity<String>("Debe ingresar usuario y clave", HttpStatus.BAD_REQUEST);

		User persistUser = userService.save(user);
		if (persistUser == null)
			return new ResponseEntity<String>("Error al ingresar usario", HttpStatus.INTERNAL_SERVER_ERROR);

		return new ResponseEntity<String>("Usuario ingresado correctamente.", HttpStatus.OK);
	}

	@GetMapping("/getuser")
	public ResponseEntity<String> decode(@RequestHeader("Authorization") String authorizationHeader) {
		if (authorizationHeader.length() < 10) {
			return new ResponseEntity<>("Debe enviar el token.", HttpStatus.UNAUTHORIZED);
		}
		String token = authorizationHeader.substring(7);
		try {
			String name = jwtUtil.extractClaims(token).getSubject();
			return new ResponseEntity<>(name, HttpStatus.OK);
		} catch (ExpiredJwtException e) {
			return new ResponseEntity<>("Token ha expirado.", HttpStatus.UNAUTHORIZED);
		}

	}
}
