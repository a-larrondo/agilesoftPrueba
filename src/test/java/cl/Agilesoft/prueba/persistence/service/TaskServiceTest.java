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

import cl.Agilesoft.prueba.persistence.model.Task;
import cl.Agilesoft.prueba.persistence.repository.TaskRepo;

@SpringBootTest
public class TaskServiceTest {

	@Mock
	TaskRepo taskRepo;

	@InjectMocks
	TaskService taskService;

	private Task task;

	@BeforeEach
	public void setUp() {
		// Crear una tarea de ejemplo
		task = new Task();
		task.setId(1L);
		task.setName("Test Task");
		task.setDescription("This is a test task");
		task.setIsComplete(false);
		task.setCreatedAt(new Date());
		task.setLastModified(new Date());
	}

	@Test
	public void completeTask() {
		task.setIsComplete(false);

		when(taskRepo.findById(1L)).thenReturn(Optional.of(task));
		when(taskRepo.save(task)).thenReturn(task);

		Task updatedTask = taskService.findById(1L).get();
		updatedTask.setIsComplete(true);
		taskService.save(updatedTask);

		assertTrue(taskService.findById(1L).get().getIsComplete());
	}

	@Test
	public void noCompleteTask() {
		task.setIsComplete(true);

		when(taskRepo.findById(1L)).thenReturn(Optional.of(task));
		when(taskRepo.save(task)).thenReturn(task);

		Task updatedTask = taskService.findById(1L).get();
		updatedTask.setIsComplete(false);
		Task result = taskService.save(updatedTask);

		assertFalse(result.getIsComplete());
	}

	@Test
	public void deleteTask() {
		doNothing().when(taskRepo).deleteById(1L);

		taskService.delete(task);
		Optional<Task> deleteTask = taskService.findById(1L);
		assertTrue(deleteTask.isEmpty());

	}
}
