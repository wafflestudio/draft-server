package com.wafflestudio.draft;

import com.wafflestudio.draft.security.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableConfigurationProperties(JwtProperties.class)
@SpringBootApplication
public class DraftApplication {

    public static void main(String[] args) {
        SpringApplication.run(DraftApplication.class, args);
    }

}
