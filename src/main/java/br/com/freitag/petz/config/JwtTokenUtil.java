package br.com.freitag.petz.config;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = 2494912226541455093L;
	public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 8*60*60;
	public static final String SIGNING_KEY = "freitag-petz-123456789-sk";

	public String getDocumentoFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
	return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser()
			.setSigningKey(SIGNING_KEY)
			.parseClaimsJws(token)
			.getBody();
	}

	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateToken(String subject, String userRole) {
	   	Claims claims = Jwts.claims().setSubject(subject);
		claims.put("authorities", Arrays.asList(userRole));

		return Jwts.builder()
			.setClaims(claims)
			.setIssuer("http://freitag.net.br")
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
			.signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
			.compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String documento = getDocumentoFromToken(token);
		return (documento.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
