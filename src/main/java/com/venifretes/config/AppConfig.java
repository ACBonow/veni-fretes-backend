package com.venifretes.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@Slf4j
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(10))
                .interceptors((request, body, execution) -> {
                    long startTime = System.currentTimeMillis();
                    ClientHttpResponse response = execution.execute(request, body);
                    long duration = System.currentTimeMillis() - startTime;

                    if (duration > 2000) {
                        log.warn("Chamada HTTP externa lenta: uri={}, method={}, duration={}ms",
                            request.getURI(), request.getMethod(), duration);
                    }

                    return response;
                })
                .build();
    }
}
