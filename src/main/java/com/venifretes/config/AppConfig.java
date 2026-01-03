package com.venifretes.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Configurar timeouts
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000);  // 5 segundos
        factory.setReadTimeout(10000);    // 10 segundos
        restTemplate.setRequestFactory(factory);

        // Interceptor para logar chamadas lentas
        restTemplate.getInterceptors().add((request, body, execution) -> {
            long startTime = System.currentTimeMillis();
            ClientHttpResponse response = execution.execute(request, body);
            long duration = System.currentTimeMillis() - startTime;

            if (duration > 2000) {
                log.warn("Chamada HTTP externa lenta: uri={}, method={}, duration={}ms",
                    request.getURI(), request.getMethod(), duration);
            }

            return response;
        });

        return restTemplate;
    }
}
