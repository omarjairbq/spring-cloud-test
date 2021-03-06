package mx.com.omarjbq.zuul.oauth;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
@RefreshScope
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Value("${config.security.oauth.jwt.key}")
	private String signingKey;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(this.tokenStorage());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/api/security/oauth/token").permitAll()
				.antMatchers(HttpMethod.GET, "/api/msv-productos/productos", "/api/msv-items/items",
						"/api/msv-usuarios/usuarios")
				.permitAll()
				.antMatchers(HttpMethod.GET, "/api/msv-productos/productos/{id}",
						"/api/msv-items/items/{id}/cantidad/{cantidad}", "/api/msv-usuarios/usuarios/{id}")
				.hasAnyRole("ADMIN", "USER")
				.antMatchers("/api/msv-productos/**", "/api/msv-items/**", "/api/msv-usuarios/**").hasRole("ADMIN")
				.anyRequest().authenticated();
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey(Base64.getEncoder().encodeToString(this.signingKey.getBytes()));

		return jwtAccessTokenConverter;
	}

	public JwtTokenStore tokenStorage() {
		return new JwtTokenStore(this.accessTokenConverter());
	}
}
