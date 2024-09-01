package cl.Agilesoft.prueba.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Service
public class JwtUtil {

	@Value("${spring.jwt.secretkey}")
	private String secretK;

	@Value("${spring.jwt.plusHourExpiration}")
	private Integer hoursRemains;

	public String generateToken(String user) {
		return createToken(user);
	}

	private String createToken(String user) {

		SecretKey secretKey = new SecretKeySpec(encodeBase64(secretK), "HmacSHA512");

		return Jwts.builder().subject(user).issuedAt(new Date()).expiration(expDate()).signWith(secretKey).compact();

	}

	public Claims extractClaims(String token) {

		SecretKey secretKey = new SecretKeySpec(encodeBase64(secretK), "HmacSHA512"); // Jwts.SIG.HS256
		Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();

//		if (claims.getExpiration().before(new Date())) {
//			throw new JwtException("El token ha expirado.");
//		}

		return claims;
	}

	private Date expDate() {

		LocalDateTime expDate = LocalDateTime.now().plusHours(hoursRemains);

		return Date.from(expDate.atZone(ZoneId.systemDefault()).toInstant());
	}

	private byte[] encodeBase64(String data) {
		return Base64.getEncoder().encode(data.getBytes());
	}

	private byte[] decodeBase64(String data) {
		return Base64.getDecoder().decode(data);
	}

}
