package com.institute.payment.config;
import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
@RequiredArgsConstructor
public class FeignConfig {
    private final HttpServletRequest request;
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {

            String authHeader =
                    request.getHeader("Authorization");
            System.out.println("AUTH HEADER = " + authHeader);
            if (authHeader != null &&
                    authHeader.startsWith("Bearer ")) {

                template.header(
                        "Authorization",
                        authHeader
                );
            }
        };
    }
}