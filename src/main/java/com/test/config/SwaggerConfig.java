package com.test.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Java Streams API Examples")
                .version("1.0")
                .description("""
                    Comprehensive Java Streams API examples demonstrating:
                    - Traditional vs Stream approaches
                    - Filter, Map, Reduce operations
                    - Aggregations and Grouping
                    - Practical real-world use cases
                    
                    Each endpoint shows execution time comparison between traditional loops and stream operations.
                    
                    Sample input data provided in endpoint descriptions.
                    """)
                .contact(new Contact()
                    .name("Haritha Reddy")
                    .email("developerstand76@gmail.com")
                    .url("https://github.com/developerstand76-ui/javastreams"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
