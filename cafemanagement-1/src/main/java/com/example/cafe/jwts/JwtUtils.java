
package com.example.cafe.jwts;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	public static final String SECRET = "367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566859703373367639792F423F4528482B4D6251655468576D5A7134743753675661359703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566859703373367639792F423F4528482B4D6251655468576D5A713474375367566859703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A713474375367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    public static final String SECRET1 = "413F4428472B4B6250655368566D5970337336763979244226452948404D6351";

	public String genreteToken(String email) {
		Map<String, Object>claims=new HashMap<>();
		return createToken(claims,email);
	}

	private String createToken(Map<String, Object> claims, String email) {
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(email)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
				.signWith(getSignKey(),SignatureAlgorithm.HS256 ).compact();
	}

	private SecretKey getSignKey() {
		
		byte[] keybyte=Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keybyte);
	}
	
	
	public String generateToken (UserDetails userDetails) { 
		return generateToken(new HashMap<>(), userDetails);  
		}

	private String generateToken (Map<String, Object> extraClaims, UserDetails userDetails) 
	{
		return Jwts.builder()
				.setClaims (extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis())) 
				.setExpiration(new Date(System.currentTimeMillis()+ 1000*60*24)) 
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	

	private Key getSigningKey() {

		byte[] keybytes=Decoders.BASE64.decode(SECRET1);
		return Keys.hmacShaKeyFor(keybytes);
	}
	public String extractUserName (String token) {
		return extractClaim (token, Claims::getSubject);
	}

	
	public boolean isTokenValid(String token, UserDetails userDetails) {
			final String userName = extractUserName(token);
			return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	

	private boolean isTokenExpired(String token) {
		
		return extractExpiration(token).before(new Date());
	}

	private <T> T extractClaim (String token, Function<Claims, T> claimsResolvers) {
			final Claims claims = extractAllClaims (token);
				return claimsResolvers.apply(claims);
	}

	private Date extractExpiration (String token) {
			return extractClaim (token, Claims::getExpiration);
	}

	private Claims extractAllClaims (String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}
}