package org.tbulens.sso.server

import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.Binding
import org.tbulens.sso.server.login.LoginRequestProcessor

@Configuration
class ServerConfig {

    private static class SsoServerConfig {

        @Bean
        public Queue queue() {
            return new Queue("login");
        }

        @Bean
        public DirectExchange exchange() {
            return new DirectExchange("sso.rpc");
        }

        @Bean
        public Binding loginBinding(DirectExchange exchange, Queue queue) {
            return BindingBuilder.bind(queue).to(exchange).with("login");
        }

        @Bean
        LoginRequestProcessor server() {
            return new LoginRequestProcessor();
        }
    }

}
