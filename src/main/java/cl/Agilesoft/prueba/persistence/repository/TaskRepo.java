package cl.Agilesoft.prueba.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.Agilesoft.prueba.persistence.model.Task;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

	Optional<Task> findByName(String name);

	@Query("SELECT t FROM Task t JOIN t.user u WHERE u.username = :username")
	List<Task> findAllTasksByUsername(@Param("username") String username);
}
