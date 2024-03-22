package dev.bujiku.springbootjooqskeleton;

import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRedisDocumentRepositories
public class SpringBootJooqSkeletonApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJooqSkeletonApplication.class, args);
	}

}
