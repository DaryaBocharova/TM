package ru.bocharova.se;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.bocharova.se.controller.Bootstrap;

@ComponentScan("ru.bocharova.se")
@Configuration
public class App {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(App.class);
        Bootstrap boot = applicationContext.getBean(Bootstrap.class);
        boot.start();
    }
}
