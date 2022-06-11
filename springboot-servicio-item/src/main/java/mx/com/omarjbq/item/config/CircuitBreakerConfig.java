package mx.com.omarjbq.item.config;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.timelimiter.TimeLimiterConfig;

/**
 * Config Circuit Breaker.
 * 
 * @author Omar Balbuena
 *
 */
@Configuration
public class CircuitBreakerConfig {

	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCostumizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()
						.slidingWindowSize(10).failureRateThreshold(50).waitDurationInOpenState(Duration.ofSeconds(10L))
						.permittedNumberOfCallsInHalfOpenState(5).slowCallRateThreshold(50)
						.slowCallDurationThreshold(Duration.ofSeconds(2L)).build())
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(6L)).build()).build());

	}
}
