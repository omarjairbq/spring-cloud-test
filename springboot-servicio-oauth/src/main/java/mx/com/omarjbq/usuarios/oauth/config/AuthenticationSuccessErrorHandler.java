package mx.com.omarjbq.usuarios.oauth.config;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {

		if (!(authentication.getDetails() instanceof WebAuthenticationDetails)) {
			UserDetails user = (UserDetails) authentication.getPrincipal();

			log.debug("LOGIN SUCCESS FOR: {}", user.getUsername());
		}
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		log.error(ExceptionUtils.getFullStackTrace(exception));
	}

}
