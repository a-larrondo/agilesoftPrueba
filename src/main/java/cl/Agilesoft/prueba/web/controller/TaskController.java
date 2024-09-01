package cl.Agilesoft.prueba.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cl.Agilesoft.prueba.persistence.model.Task;
import cl.Agilesoft.prueba.persistence.model.User;
import cl.Agilesoft.prueba.persistence.service.TaskService;
import cl.Agilesoft.prueba.persistence.service.UserService;
import cl.Agilesoft.prueba.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private TaskService taskService;

	@Autowired
	private UserService userService;

	@GetMapping("/getAllTasks")
	@ResponseBody
	public ResponseEntity<?> getTasks(@RequestHeader("Authorization") String authorizationHeader) {

		if (authorizationHeader.length() < 10) {
			return new ResponseEntity<>("Debe enviar el token.", HttpStatus.UNAUTHORIZED);
		}
		String token = authorizationHeader.substring(7);
		try {
			String name = jwtUtil.extractClaims(token).getSubject();
			List<Task> tasks = taskService.findAllTaskByUsername(name);

			return new ResponseEntity<>(tasks, HttpStatus.OK);

		} catch (ExpiredJwtException e) {
			return new ResponseEntity<>("Token ha expirado.", HttpStatus.UNAUTHORIZED);
		} catch (MalformedJwtException e) {
			return new ResponseEntity<>("Formato de token no valido.", HttpStatus.BAD_REQUEST);

		}

	}

	@PostMapping("/createTask")
	public ResponseEntity<?> createTask(@RequestHeader("Authorization") String authorizationHeader,
			@RequestBody Task task) {
		try {
			if (authorizationHeader.length() < 10) {
				return new ResponseEntity<>("Debe enviar el token.", HttpStatus.UNAUTHORIZED);
			}
			String token = authorizationHeader.substring(7);
			String name = jwtUtil.extractClaims(token).getSubject();
			Optional<User> user = userService.findByUsername(name);

			if (user.isEmpty()) {
				return new ResponseEntity<>("Usuario no encontrado", HttpStatus.UNAUTHORIZED);
			}

			Date now = new Date();
			task.setCreatedAt(now);
			task.setLastModified(now);
			task.setUser(user.get());

			Task createdTask = taskService.save(task);
			return new ResponseEntity<>(createdTask, HttpStatus.CREATED);

		} catch (ExpiredJwtException e) {
			return new ResponseEntity<>("Token ha expirado.", HttpStatus.UNAUTHORIZED);
		} catch (MalformedJwtException e) {
			return new ResponseEntity<>("Formato de token no valido.", HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/complete/{id}")
	public ResponseEntity<?> updateStatus(@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable("id") Long id) {
		try {
			Optional<Task> taskToUpdate = taskService.findById(id);
			if (taskToUpdate.isEmpty()) {
				return new ResponseEntity<>("No existe tarea con el id enviado.", HttpStatus.BAD_REQUEST);
			}
			if (authorizationHeader.length() < 10) {
				return new ResponseEntity<>("Debe enviar el token.", HttpStatus.UNAUTHORIZED);
			}

			String token = authorizationHeader.substring(7);
			String name = jwtUtil.extractClaims(token).getSubject();
			Optional<User> user = userService.findByUsername(name);

			if (user.isEmpty()) {
				return new ResponseEntity<>("Usuario no encontrado", HttpStatus.UNAUTHORIZED);
			}

			taskToUpdate.get().setIsComplete(true);

			Task createdTask = taskService.save(taskToUpdate.get());
			return new ResponseEntity<>(createdTask, HttpStatus.CREATED);

		} catch (ExpiredJwtException e) {
			return new ResponseEntity<>("Token ha expirado.", HttpStatus.UNAUTHORIZED);
		} catch (MalformedJwtException e) {
			return new ResponseEntity<>("Formato de token no valido.", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteTask(@RequestHeader("Authorization") String authorizationHeader,
			@PathVariable("id") Long id) {
		try {
			Optional<Task> taskToDelete = taskService.findById(id);
			if (taskToDelete.isEmpty()) {
				return new ResponseEntity<>("No existe tarea con el id enviado.", HttpStatus.BAD_REQUEST);
			}
			if (authorizationHeader.length() < 10) {
				return new ResponseEntity<>("Debe enviar el token.", HttpStatus.UNAUTHORIZED);
			}

			String token = authorizationHeader.substring(7);
			String name = jwtUtil.extractClaims(token).getSubject();
			Optional<User> user = userService.findByUsername(name);

			if (user.isEmpty()) {
				return new ResponseEntity<>("Usuario no encontrado", HttpStatus.UNAUTHORIZED);
			}

			taskService.delete(taskToDelete.get());
			return new ResponseEntity<>("Tarea borrada.", HttpStatus.OK);

		} catch (ExpiredJwtException e) {
			return new ResponseEntity<>("Token ha expirado.", HttpStatus.UNAUTHORIZED);
		} catch (MalformedJwtException e) {
			return new ResponseEntity<>("Formato de token no valido.", HttpStatus.BAD_REQUEST);
		}
	}

}
