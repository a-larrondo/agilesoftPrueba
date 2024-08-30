package cl.Agilesoft.prueba.service;

import cl.Agilesoft.prueba.models.dao.User;
import cl.Agilesoft.prueba.repository.UserRepo;

public class UserService {
	private UserRepo userRepo;
	
	public User save(User user) {
		return userRepo.save(user);
	}
	
	

}
