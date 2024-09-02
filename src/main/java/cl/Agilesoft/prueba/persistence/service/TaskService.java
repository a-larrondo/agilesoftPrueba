package cl.Agilesoft.prueba.persistence.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.Agilesoft.prueba.persistence.model.Task;
import cl.Agilesoft.prueba.persistence.repository.TaskRepo;

@Service
public class TaskService {

	@Autowired
	TaskRepo taskRepo;

	public Task save(Task task) {

		return taskRepo.save(task);
	}

	public void delete(Task task) {
		taskRepo.delete(task);
	}

	public Optional<Task> findByName(String name) {
		return taskRepo.findByName(name);
	}

	public List<Task> findAllTaskByUsername(String userName) {
		return taskRepo.findAllTasksByUsername(userName);
	}

	public Optional<Task> findByIdName(Long id, String userName) {
		
		return taskRepo.findByIdAndName(id,userName);
	}

}
