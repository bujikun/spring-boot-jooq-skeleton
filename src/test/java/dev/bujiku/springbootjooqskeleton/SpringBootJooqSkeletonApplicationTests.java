package dev.bujiku.springbootjooqskeleton;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestSpringBootJooqSkeletonApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class SpringBootJooqSkeletonApplicationTests {
    @Autowired
    PostgreSQLContainer postgreSQLContainer;
    @Test
    @Order(1)
    @DisplayName("Verify Container Running")
    void contextLoads() {
        assertThat(postgreSQLContainer.isRunning()).isTrue();
    }

}
