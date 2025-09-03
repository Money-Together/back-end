package com.example.moneytogether1.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

    @Bean
    public Dotenv dotenv() {
        // .env 파일이 없어도 에러 나지 않도록 ignoreIfMissing()
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        // 필요한 환경 변수 안전하게 등록
        setSystemPropertyIfNotNull(dotenv, "JWT_SECRET");
        setSystemPropertyIfNotNull(dotenv, "MYSQL_DATABASE");
        setSystemPropertyIfNotNull(dotenv, "MYSQL_USER");
        setSystemPropertyIfNotNull(dotenv, "MYSQL_PASSWORD");
        setSystemPropertyIfNotNull(dotenv, "FILE_UPLOAD_DIR"); // 필요하면 추가

        return dotenv;
    }

    private void setSystemPropertyIfNotNull(Dotenv dotenv, String key) {
        String value = dotenv.get(key);
        if (value != null) {
            System.setProperty(key, value);
        }
    }
}
