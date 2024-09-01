package cl.Agilesoft.prueba.persistence.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.Agilesoft.prueba.persistence.model.User;
import cl.Agilesoft.prueba.persistence.repository.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public User save(User user) {

		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		return userRepo.save(user);
	}

	public Optional<User> findByUsername(String userName) {
		return userRepo.findByUsername(userName);
	}

	public Boolean validateUser(String userName, String password) {
		Optional<User> user = this.findByUsername(userName);
		if (user.isEmpty()) {
			return false;
		}

		return passwordEncoder.matches(password, user.get().getPassword());

	}

}
