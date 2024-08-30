package cl.Agilesoft.prueba.persistence.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import cl.Agilesoft.prueba.persistence.model.User;
import cl.Agilesoft.prueba.persistence.repository.UserRepo;

@SpringBootTest
class UserServiceTests {

	@Mock
	private UserRepo userRepo;
	
	@InjectMocks
	private UserService userService;
	
	String nom = "aldo";
	String pass = "1234";
	
	@BeforeEach
	public void setUp() {
		User user = new User();
		user.setPassword(pass);
		user.setUsername(nom);
		
		when(userRepo.findByUsername(nom)).thenReturn(Optional.of(user));
	}
	
	@Test
	public void wrongUsernameWhenFind() {		
		
		Optional<User> user1 = userService.findByUsername("aldo1");		
		assertThat(user1.isEmpty());
	}
	
	@Test
	public void correctUsernameWhenFind() {		
		
		Optional<User> user = userService.findByUsername("aldo");		
		assertThat(user.isPresent());
		assertThat(user.get().getPassword()).isEqualTo(pass);
	}

}
