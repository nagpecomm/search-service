# Business Search

This project is updated to **Java 25** and **Spring Boot 4.x**.

## 🚨 Breaking Changes from Spring Boot 3.x → 4.x

### 1. Jackson Package Changes

**Spring Boot 3.x**

```java
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
```

**Spring Boot 4.x**

```java
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
```

Spring Boot 4 ships with the new **Tools Jackson** fork, so imports must be updated.

### 2. TestRestTemplate Setup Change

Spring Boot 4.x requires an additional dependency to enable `TestRestTemplate`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-restclient-test</artifactId>
    <scope>test</scope>
</dependency>
```

To use `TestRestTemplate` in tests, you must now explicitly enable it:

```java
@SpringBootTest
@AutoConfigureTestRestTemplate
public class BusinessSearchTest {

    @Autowired
    private TestRestTemplate restTemplate;

}
```

## 🧪 Updated Testcontainers Configuration (Elasticsearch)

Please use the updated Testcontainers configuration for Elasticsearch.

```java
@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    ElasticsearchContainer elasticsearchContainer() {
        return new ElasticsearchContainer(
                DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:9.0.2")
        )
        .withEnv("xpack.security.enabled", "false")
        .withEnv("xpack.security.http.ssl.enabled", "false");
    }
}
```

https://auth0.com/