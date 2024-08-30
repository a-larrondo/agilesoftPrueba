package cl.Agilesoft.prueba.persistence.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.Agilesoft.prueba.persistence.model.User;
import cl.Agilesoft.prueba.persistence.repository.UserRepo;

@Service
public class UserService {
	private UserRepo userRepo;

	public User save(User user) {
		return userRepo.save(user);
	}

	public Optional<User> findByUsername(String userName) {
		return userRepo.findByUsername(userName);
	}

	public Boolean validateUser(String userName, String password) {
		Optional<User> user = this.findByUsername(userName);
		if (user.isEmpty())
			return false;

		return user.get().getPassword().equals(password);

	}

}
