package com.example.lab6;

import com.example.lab6.config.AppConfig;
import com.example.lab6.service.GreetingService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class TutorialsApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Use Case 1: Bean Creation - retrieve GreetingService and print greeting
        System.out.println("=== Use Case 1: Bean Creation ===");
        GreetingService greetingService = context.getBean("greetingService", GreetingService.class);
        System.out.println(greetingService.sayHello());

        // Use Case 2: Dependency Injection via GreetingPrinter (@Autowired constructor injection)
        System.out.println("\n=== Use Case 2: Dependency Injection ===");
        GreetingPrinter greetingPrinter = context.getBean(GreetingPrinter.class);
        greetingPrinter.printMessage();

        // Use Case 3: Singleton Scope - both calls return the same instance
        System.out.println("\n=== Use Case 3: Singleton Scope ===");
        GreetingService singleton1 = context.getBean("greetingService", GreetingService.class);
        GreetingService singleton2 = context.getBean("greetingService", GreetingService.class);
        System.out.println("Bean retrieved twice. Same instance? " + (singleton1 == singleton2));

        // Use Case 4: Prototype Scope - each call returns a new instance
        System.out.println("\n=== Use Case 4: Prototype Scope ===");
        GreetingService prototype1 = context.getBean("prototypeGreetingService", GreetingService.class);
        GreetingService prototype2 = context.getBean("prototypeGreetingService", GreetingService.class);
        System.out.println("Bean retrieved twice. Same instance? " + (prototype1 == prototype2));

        // Closing context triggers @PreDestroy on singleton beans
        System.out.println("\n=== Closing Application Context ===");
        context.close();
    }
}
