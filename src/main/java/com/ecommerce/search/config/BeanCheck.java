package com.ecommerce.search.config;

import org.springframework.context.ApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

@Component
public class BeanCheck {

    public BeanCheck(ApplicationContext context) {
        System.out.println("ElasticsearchOperations Bean: " +
                context.getBean(ElasticsearchOperations.class).getClass());
    }
}
