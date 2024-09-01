package org.springframework.stereotype.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import cl.Agilesoft.prueba.PruebaApplication;
import cl.Agilesoft.prueba.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@SpringBootTest(classes = PruebaApplication.class)
public class JwtUtilTests {

	@Autowired
	private JwtUtil jwtUtil;

	@Value("${spring.jwt.secretkey}")
	private String secretK;

	Claims claims;
	String token, username;

	@BeforeEach
	void setUp() {
		username = "aldo";
		token = jwtUtil.generateToken(username);
		claims = jwtUtil.extractClaims(token);
	}

	@Test
	public void generateToken() {
		assertThat(claims).isNotNull();
	}

	@Test
	public void obtainUsername() {
		assertThat(claims.getSubject()).isEqualTo(username);
	}

	@Test
	public void obtainIsueAtDate() {
		assertThat(claims.getIssuedAt()).isNotNull();
	}

	@Test
	public void obtainExpirationDate() {
		assertThat(claims.getExpiration()).isNotNull();
	}

	@Test
	public void verificarFechaExpiracionDespuesDeActual() {
		Date expirationDate = claims.getExpiration();
		assertThat(expirationDate).isAfter(new Date());
	}

	@Test
	public void expiredToken() throws InterruptedException {

		Date expDate = new Date();
//		Thread.sleep(800);
		byte[] encodeBase64 = Base64.getEncoder().encode(secretK.getBytes());
		SecretKey secretKey = new SecretKeySpec(encodeBase64, "HmacSHA512");

		String expiratedToken = Jwts.builder().subject("test").issuedAt(new Date()).expiration(expDate)
				.signWith(secretKey).compact();

		JwtException thrown = assertThrows(ExpiredJwtException.class, () -> {
			jwtUtil.extractClaims(expiratedToken);
		});
		assertThat(thrown.getMessage()).contains("JWT expired");

	}

}
