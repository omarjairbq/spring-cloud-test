package mx.com.omarjbq.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {

	@Autowired
	private JWTAuthenticationFilter authenticationFilter;

	@Bean
	public SecurityWebFilterChain configure(ServerHttpSecurity http) {
		return http.authorizeExchange().pathMatchers("/api/security/oauth/**").permitAll()
				.pathMatchers(HttpMethod.GET, "/api/msv-productos/productos", "/api/msv-items/items",
						"/api/msv-usuarios/usuarios")
				.permitAll()
				.pathMatchers(HttpMethod.GET, "/api/msv-productos/productos/{id}",
						"/api/msv-items/items/{id}/cantidad/{cantidad}", "/api/msv-usuarios/usuarios/{id}")
				.hasAnyRole("ADMIN", "USER")
				.pathMatchers("/api/msv-productos/**", "/api/msv-items/**", "/api/msv-usuarios/**").hasRole("ADMIN")
				.anyExchange().authenticated().and()
				.addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION).csrf().disable().build();
	}
}
