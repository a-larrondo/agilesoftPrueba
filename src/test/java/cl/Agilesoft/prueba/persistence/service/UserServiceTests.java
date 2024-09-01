package cl.Agilesoft.prueba.persistence.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import cl.Agilesoft.prueba.persistence.model.User;
import cl.Agilesoft.prueba.persistence.repository.UserRepo;

@SpringBootTest
class UserServiceTests {

	@Mock
	private UserRepo userRepo;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserService userService;

	String nom = "aldo";
	String pass = "1234";
	String passEncryp = passwordEncoder.encode(pass); // "$2a$10$/ZBw8mp/Ta832vqs6z9BxuP5vkvvPG0MfyPzgYtppqX/W2KxobOAO";

	@BeforeEach
	public void setUp() {
		User user = new User();
		user.setPassword(passEncryp);
		user.setUsername(nom);

		when(userRepo.findByUsername(nom)).thenReturn(Optional.of(user));
		when(userService.validateUser(nom, pass)).thenReturn(true);
	}

	@Test
	public void wrongUsernameWhenFind() {

		Optional<User> user1 = userService.findByUsername("aldo1");
		assertTrue(user1.isEmpty());
	}

	@Test
	public void correctUsernameWhenFind() {

		Optional<User> user = userService.findByUsername("aldo");
		assertTrue(user.isPresent());
		assertEquals(passEncryp, user.get().getPassword());
	}

	@Test
	public void authenticateUserValidPass() {
		assertTrue(userService.validateUser(nom, pass));
	}

	@Test
	public void authenticateUserInvalidPass() {
		assertFalse(userService.validateUser(nom, pass + "1"));
	}
}
