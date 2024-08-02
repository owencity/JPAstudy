package jjon.pop.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {

	private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
	private static final SecretKey key = Jwts.SIG.HS256.key().build();
	
	public String generateAccessToken(UserDetails userDetails) {
		return generateToken(userDetails.getUsername());
	}
	
	public String getUsername(String accessToken) {
		return getSubject(accessToken);
	}
	
	private String generateToken(String subject) {
		var now = new Date();
		var exp = new Date(now.getTime() + (1000 * 60 * 60 * 3));
		return Jwts.builder().subject(subject).signWith(key).issuedAt(now).expiration(exp).compact();
		// subject -> 페이로드안에 클레임을 설정한것
	}

	private String getSubject(String token) {
		try {
			return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
		} catch (JwtException e) {
			logger.error("JwtExeption", e);
			throw e;
		}

	}
}
