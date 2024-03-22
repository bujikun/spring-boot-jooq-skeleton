package dev.bujiku.springbootjooqskeleton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestSpringBootJooqSkeletonApplication {

	public static void main(String[] args) {
		SpringApplication.from(SpringBootJooqSkeletonApplication::main).with(TestSpringBootJooqSkeletonApplication.class).run(args);
	}

	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		return new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.2-alpine3.19"))
				.withReuse(true);
	}

	@Bean
	@ServiceConnection
	RabbitMQContainer rabbitContainer() {

		return new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.13"))
				.withReuse(true);
	}

	@Bean
	@ServiceConnection(name = "redis")
	GenericContainer<?> redisContainer() {
		return new GenericContainer<>(DockerImageName.parse("redis/redis-stack:7.2.0-v9"))
				.withExposedPorts(6379,8001)
				.withReuse(true);
	}

}
