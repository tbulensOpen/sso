package org.tbulens.sso.server

import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.Binding

@Configuration
class ServerConfig {

    private static class SsoServerConfig {

        @Bean
        public Queue queue() {
            return new Queue("sso.rpc.requests");
        }

        @Bean
        public DirectExchange exchange() {
            return new DirectExchange("sso.rpc");
        }

        @Bean
        public Binding binding(DirectExchange exchange, Queue queue) {
            return BindingBuilder.bind(queue).to(exchange).with("rpc");
        }

        @Bean
        LoginRequestProcessor server() {
            return new LoginRequestProcessor();
        }

    }

}
