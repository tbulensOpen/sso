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
import org.tbulens.sso.server.logout.LogoutRequestProcessor

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
        public Queue logoutQueue() {
            return new Queue("logout");
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
        public Binding logoutBinding(DirectExchange exchange, Queue logoutQueue) {
            return BindingBuilder.bind(logoutQueue).to(exchange).with("logout");
        }

        @Bean
        LoginRequestProcessor loginServer() {
            return new LoginRequestProcessor();
        }

        @Bean
        AuthenticateRequestProcessor authenticateServer() {
            return new AuthenticateRequestProcessor();
        }

        @Bean
        LogoutRequestProcessor logoutServer() {
            return new LogoutRequestProcessor();
        }
    }

}
