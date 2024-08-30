package cl.Agilesoft.prueba.persistence.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import cl.Agilesoft.prueba.persistence.model.User;

@Repository
public interface UserRepo  extends GenericModelRepository<User, Long> {

	Optional<User> findByUsername(String username);
	

}
