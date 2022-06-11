package mx.com.omarjbq.gateway.filters.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class EjemploGatewayFilterFactory extends AbstractGatewayFilterFactory<EjemploGatewayFilterFactory.Config> {

	private final Logger LOGGER = LoggerFactory.getLogger(EjemploGatewayFilterFactory.class);

	public EjemploGatewayFilterFactory() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			LOGGER.info("EJECUTANDO PRE EjemploGatewayFilterFactory: {}", config);
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				LOGGER.info("EJECUTANDO POST EjemploGatewayFilterFactory: {}", config);

				Optional.ofNullable(config.cookieValue).ifPresent(value -> {
					exchange.getResponse().addCookie(ResponseCookie.from(config.cookieKey, value).build());
				});
			}));
		};
	}

	@Override
	public String name() {
		return "EjemploCookie";
	}

	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList("mensaje", "cookieKey", "cookieValue");
	}

	public static class Config {
		private String mensaje;
		private String cookieKey;
		private String cookieValue;

		public String getMensaje() {
			return mensaje;
		}

		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}

		public String getCookieKey() {
			return cookieKey;
		}

		public void setCookieKey(String cookieKey) {
			this.cookieKey = cookieKey;
		}

		public String getCookieValue() {
			return cookieValue;
		}

		public void setCookieValue(String cookieValue) {
			this.cookieValue = cookieValue;
		}

		@Override
		public String toString() {
			return "Config [mensaje=" + mensaje + ", cookieKey=" + cookieKey + ", cookieValue=" + cookieValue + "]";
		}

	}
}
