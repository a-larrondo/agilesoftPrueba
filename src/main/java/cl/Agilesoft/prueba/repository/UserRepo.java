package cl.Agilesoft.prueba.repository;

import org.springframework.stereotype.Repository;

import cl.Agilesoft.prueba.models.dao.User;

@Repository
public interface UserRepo  extends GenericModelRepository<User, Long> {

	User findByUsername(String username);
	

}
