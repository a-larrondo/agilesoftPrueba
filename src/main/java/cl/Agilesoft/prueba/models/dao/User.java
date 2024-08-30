package cl.Agilesoft.prueba.models.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
	@Id
	private Long id;
	private String username;
	private String password;
	
	
}
