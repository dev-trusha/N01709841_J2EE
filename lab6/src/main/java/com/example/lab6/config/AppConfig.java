package com.example.lab6.config;

import com.example.lab6.service.GreetingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = "com.example.lab6")
public class AppConfig {

    // Singleton scope (default) - same instance returned every time
    @Bean
    public GreetingService greetingService() {
        return new GreetingService();
    }

    // Prototype scope - new instance created for every getBean() call
    @Bean
    @Scope("prototype")
    public GreetingService prototypeGreetingService() {
        return new GreetingService();
    }
}
