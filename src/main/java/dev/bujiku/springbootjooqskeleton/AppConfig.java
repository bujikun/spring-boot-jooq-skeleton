package dev.bujiku.springbootjooqskeleton;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsonp.JSONPModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author Newton Bujiku
 * @since 2024
 */
@Configuration(proxyBeanMethods = false)
@Slf4j
@EnableAsync
public class AppConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        var timeModule = new JavaTimeModule();
        var jsonPatchModule = new JSONPModule();
        //customize the default object mapper
        return builder -> builder
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .featuresToEnable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                .failOnUnknownProperties(false)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .featuresToDisable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
                .indentOutput(true)
                .serializerByType(Instant.class, new InstantTimeSerializer())
                .modules(timeModule, jsonPatchModule);
    }

    @Bean(name = "quickTaskExecutor")
    @Primary
    public ThreadPoolTaskExecutor quickTaskExecutor() {
        var fastExecutor = new ThreadPoolTaskExecutor();
        fastExecutor.setCorePoolSize(5);
        fastExecutor.setMaxPoolSize(10);
        fastExecutor.setQueueCapacity(500);
        fastExecutor.setThreadNamePrefix("QuickAsyncTaskExecutor - ");
        fastExecutor.setRejectedExecutionHandler((r, executor1) -> {
            log.warn("TASK REJECTED ::: Both Thread Pool and Queue are full!");
        });
        fastExecutor.initialize();
        return fastExecutor;
    }

    @Bean(name = "slowTaskExecutor")
    public ThreadPoolTaskExecutor slowTaskExecutor() {
        var slowExecutor = new ThreadPoolTaskExecutor();
        slowExecutor.setCorePoolSize(5);
        slowExecutor.setMaxPoolSize(10);
        slowExecutor.setQueueCapacity(200);
        slowExecutor.setThreadNamePrefix("SlowAsyncTaskExecutor - ");
        slowExecutor.setRejectedExecutionHandler((r, executor1) -> {
            log.warn("TASK REJECTED ::: Both Thread Pool and Queue are full!");
        });
        slowExecutor.initialize();
        return slowExecutor;
    }

    private static class InstantTimeSerializer extends JsonSerializer<Instant> {
        //Serialize Instants to into proper readable format
        @Override
        public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            var dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-uuuu HH:mm:ss")
                    .withZone(ZoneId.systemDefault());
            gen.writeString(dateTimeFormatter.format(value));
        }
    }
}
