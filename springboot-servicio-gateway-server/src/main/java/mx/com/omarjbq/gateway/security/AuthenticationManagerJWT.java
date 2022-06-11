package mx.com.omarjbq.gateway.security;

import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Configuration
public class AuthenticationManagerJWT implements ReactiveAuthenticationManager {

	@Value("${config.security.oauth.jwt.key}")
	private String secretKey;

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		return Mono.just(authentication.getCredentials().toString()).map(token -> {
			byte[] secretKeyEncoded = Base64.getEncoder().encode(this.secretKey.getBytes());
			SecretKey llave = Keys.hmacShaKeyFor(secretKeyEncoded);

			return Jwts.parserBuilder().setSigningKey(llave).build().parseClaimsJws(token).getBody();
		}).map(claims -> {
			String username = claims.get("username", String.class);

			@SuppressWarnings("unchecked")
			List<String> roles = claims.get("authorities", List.class);

			Collection<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());

			return new UsernamePasswordAuthenticationToken(username, null, authorities);
		});
	}

}
