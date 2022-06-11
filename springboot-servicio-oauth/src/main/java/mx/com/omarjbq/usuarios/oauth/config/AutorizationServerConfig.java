package mx.com.omarjbq.usuarios.oauth.config;

import java.util.Arrays;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
@RefreshScope
public class AutorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${config.security.oauth.jwt.key}")
	private String signingKey;

	@Value("${config.security.oauth.client.id}")
	private String clientID;

	@Value("${config.security.oauth.client.secret}")
	private String clientSecret;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private InfoAdicionalToken infoAdicionalToken;

	/*
	 * For Zul zerver
	 * 
	 * @Bean public JwtAccessTokenConverter accessTokenConverter() {
	 * JwtAccessTokenConverter jwtAccessTokenConverter = new
	 * JwtAccessTokenConverter();
	 * jwtAccessTokenConverter.setSigningKey(this.signingKey);
	 * 
	 * return jwtAccessTokenConverter; }
	 */

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey(Base64.getEncoder().encodeToString(this.signingKey.getBytes()));

		return jwtAccessTokenConverter;
	}

	public JwtTokenStore tokenStorage() {
		return new JwtTokenStore(this.accessTokenConverter());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAutenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(this.clientID).secret(bCryptPasswordEncoder.encode(this.clientSecret))
				.scopes("read", "write").authorizedGrantTypes("password", "refresh_token")
				.refreshTokenValiditySeconds(3600);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, this.accessTokenConverter()));

		endpoints.authenticationManager(this.authenticationManager).tokenStore(this.tokenStorage())
				.accessTokenConverter(this.accessTokenConverter()).tokenEnhancer(tokenEnhancerChain);
	}

}
