package cl.Agilesoft.prueba.persistence.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import cl.Agilesoft.prueba.persistence.model.Task;
import cl.Agilesoft.prueba.persistence.model.User;
import cl.Agilesoft.prueba.persistence.repository.TaskRepo;

@SpringBootTest
public class TaskServiceTest {

	@Mock
	TaskRepo taskRepo;

	@InjectMocks
	TaskService taskService;

	@Mock
	private PasswordEncoder passwordEncoder;

	String nom = "aldo";
	String pass = "1234";
	String passEncryp = passwordEncoder.encode(pass);

	private Task task;
	private User user;

	@BeforeEach
	public void setUp() {

		user = new User();
		user.setPassword(passEncryp);
		user.setUsername(nom);

		task = new Task();
		task.setId(1L);
		task.setName("Test Task");
		task.setDescription("This is a test task");
		task.setIsComplete(false);
		task.setCreatedAt(new Date());
		task.setLastModified(new Date());
		task.setUser(user);
	}

	@Test
	public void completeTask() {
		task.setIsComplete(false);

		when(taskRepo.findById(1L)).thenReturn(Optional.of(task));
		when(taskRepo.save(task)).thenReturn(task);

		Task updatedTask = taskService.findByIdName(1L, nom).get();
		updatedTask.setIsComplete(true);
		taskService.save(updatedTask);

		assertTrue(taskService.findByIdName(1L, nom).get().getIsComplete());
	}

	@Test
	public void noCompleteTask() {
		task.setIsComplete(true);

		when(taskRepo.findById(1L)).thenReturn(Optional.of(task));
		when(taskRepo.save(task)).thenReturn(task);

		Task updatedTask = taskService.findByIdName(1L, nom).get();
		updatedTask.setIsComplete(false);
		Task result = taskService.save(updatedTask);

		assertFalse(result.getIsComplete());
	}

	@Test
	public void deleteTask() {
		doNothing().when(taskRepo).deleteById(1L);

		taskService.delete(task);
		Optional<Task> deleteTask = taskService.findByIdName(1L, nom);
		assertTrue(deleteTask.isEmpty());

	}
}
