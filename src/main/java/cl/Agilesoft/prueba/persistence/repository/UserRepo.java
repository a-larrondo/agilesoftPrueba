package cl.Agilesoft.prueba.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.Agilesoft.prueba.persistence.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);


}
