package org.tbulens.sso.loginweb

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportResource
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ImportResource("classpath:contexts/application-context.xml")
public class Application {
    
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        
//        String[] beanNames = ctx.getBeanDefinitionNames();
//        Arrays.sort(beanNames);
//        for (String beanName : beanNames) {
//            System.out.println(beanName);
//        }
    }

}
