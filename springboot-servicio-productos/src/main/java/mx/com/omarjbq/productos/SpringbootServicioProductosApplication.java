package mx.com.omarjbq.productos;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication
public class SpringbootServicioProductosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioProductosApplication.class, args);
	}

	/**
	 * @return DozerBeanMapper.
	 */
	@Bean(name = "dozerMapper")
	Mapper registerDozerMapper() {
		return new DozerBeanMapper();
	}

}
