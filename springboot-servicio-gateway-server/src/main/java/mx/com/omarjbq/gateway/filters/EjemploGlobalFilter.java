package mx.com.omarjbq.gateway.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class EjemploGlobalFilter implements GlobalFilter {

	private final Logger logger = LoggerFactory.getLogger(EjemploGlobalFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("EJECUTANDO FILTER PRE");

		exchange.getRequest().mutate().headers(header -> header.add("token", "12321312"));

		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			logger.info("EJECUTANDO FILTER POST");

			Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(header -> {
				exchange.getResponse().getHeaders().add("token", header);
			});
			exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "red").build());
		}));
	}

}
