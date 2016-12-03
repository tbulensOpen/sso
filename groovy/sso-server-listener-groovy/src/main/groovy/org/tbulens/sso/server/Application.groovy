package org.tbulens.sso.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ImportResource

@SpringBootApplication
@ImportResource("classpath:contexts/sso-server-application-context.xml")
class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

    }
}
