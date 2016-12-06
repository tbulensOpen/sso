package org.tbulens.sso.server

import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.Binding
import org.tbulens.sso.server.authenticate.AuthenticateRequestProcessor
import org.tbulens.sso.server.login.LoginRequestProcessor

@Configuration
class ServerConfig {

    private static class SsoServerConfig {

        @Bean
        public Queue loginQueue() {
            return new Queue("login");
        }

        @Bean
        public Queue authenticateQueue() {
            return new Queue("authenticate");
        }

        @Bean
        public DirectExchange exchange() {
            return new DirectExchange("sso.rpc");
        }

        @Bean
        public Binding loginBinding(DirectExchange exchange, Queue loginQueue) {
            return BindingBuilder.bind(loginQueue).to(exchange).with("login");
        }

        @Bean
        public Binding authenticateBinding(DirectExchange exchange, Queue authenticateQueue) {
            return BindingBuilder.bind(authenticateQueue).to(exchange).with("authenticate");
        }

        @Bean
        LoginRequestProcessor loginServer() {
            return new LoginRequestProcessor();
        }

        @Bean
        AuthenticateRequestProcessor authenticateServer() {
            return new AuthenticateRequestProcessor();
        }
    }

}
