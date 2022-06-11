package mx.com.omarjbq.item.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Config Rest Template.
 * 
 * @author Omar Balbuena
 *
 */
@Configuration
public class RestTemplateConfig {

	/**
	 * Bean RestTemplate.
	 * 
	 * @return RestTemplate
	 */
	@Bean(name = "restTemplate")
	@LoadBalanced
	public RestTemplate registerRestTemplate() {
		return new RestTemplate();
	}
}
