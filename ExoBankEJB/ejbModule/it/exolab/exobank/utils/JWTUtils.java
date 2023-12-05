package it.exolab.exobank.utils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import javax.crypto.SecretKey;

public class JWTUtils {
	
    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	private static final long EXPIRATION_TIME = 1800000;//MEZZORA
	
	public String generateToken(String userID) {
		return Jwts.builder()
				.setSubject(userID)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();

	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
		

}
