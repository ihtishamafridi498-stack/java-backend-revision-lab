package com.afridi;

import com.afridi.config.AppConfig;
import com.afridi.model.ResultCard;
import com.afridi.service.ResultService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {


        try (AnnotationConfigApplicationContext ctx =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {

            ResultService service = ctx.getBean("resultService", ResultService.class);
            service.printAll();

            System.out.println("Same service?  " + (service == ctx.getBean("resultService")));
            System.out.println("Same card?     "
                    + (ctx.getBean("resultCard", ResultCard.class) == ctx.getBean("resultCard", ResultCard.class)));
        }



       /* try (ClassPathXmlApplicationContext ctx =
                     new ClassPathXmlApplicationContext("spring.xml")) {

            ResultService service = ctx.getBean("resultService", ResultService.class);
            service.printAll();

            System.out.println("Same service?  " + (service == ctx.getBean("resultService")));
            System.out.println("Same card?     "
                    + (ctx.getBean("resultCard", ResultCard.class) == ctx.getBean("resultCard", ResultCard.class)));
        }*/
    }
}