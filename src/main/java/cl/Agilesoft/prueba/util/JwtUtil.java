package cl.Agilesoft.prueba.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.Comments;
import org.springframework.stereotype.Service;

import cl.Agilesoft.prueba.persistence.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	private String KEY = "Agilesoft";

	public String generateToken(String user) {

		Map<String, Object> claims = new HashMap<>();
		claims.put("rol", "user");

		return createToken(claims, user);

	}

	private String createToken(Map<String, Object> claims, String user) {

		return Jwts.builder().setClaims(claims).setSubject(user).setIssuedAt(new Date())
				.setExpiration(expDateInHours(2)).signWith(SignatureAlgorithm.NONE, KEY).compact();

	}

	private Date expDateInHours(Integer hoursRemains) {

		LocalDateTime expDate = LocalDateTime.now().plusHours(hoursRemains);

		return Date.from(expDate.atZone(ZoneId.systemDefault()).toInstant());
	}

}
