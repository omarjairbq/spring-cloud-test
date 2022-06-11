package mx.com.omarjbq.gateway.security;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class JWTAuthenticationFilter implements WebFilter {

	@Autowired
	private ReactiveAuthenticationManager reactiveAuthenticationManager;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
				.filter(authHeader -> authHeader.contains("Bearer "))
				.switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
				.map(token -> token.replace("Bearer ", StringUtils.EMPTY))
				.flatMap(token -> this.reactiveAuthenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(null, token)))
				.flatMap(authentication -> chain.filter(exchange)
						.contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)));
	}

}
