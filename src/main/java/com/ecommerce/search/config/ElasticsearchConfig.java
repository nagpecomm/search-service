package com.ecommerce.search.config;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Bean
    public RestClientBuilderCustomizer customizer() {
        return builder -> builder.setDefaultHeaders(
                new Header[]{
                        new BasicHeader("Authorization",
                                "ApiKey Vk1NWXM1d0IxQlptX0ZCbm5VWnc6dFZ4aUZTNFJDbHYwZy1mNFc5OGxCZw==")
                }
        );
    }
}
