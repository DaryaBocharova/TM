package ru.bocharova.se;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.bocharova.se.controller.Bootstrap;

@ComponentScan
public class App {

    public static void main(String[] args) throws Exception {
        ApplicationContext context =
                new AnnotationConfigApplicationContext("ru.bocharova.se");
        Bootstrap bootstrap = context.getBean(Bootstrap.class);
        bootstrap.init();
    }

}
