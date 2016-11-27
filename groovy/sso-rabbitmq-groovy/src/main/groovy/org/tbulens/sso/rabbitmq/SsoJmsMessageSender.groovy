package org.tbulens.sso.rabbitmq

import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.jms.admin.RMQConnectionFactory
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.core.MessageCreator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@EnableJms
@Component
class SsoJmsMessageSender {
    @Autowired  AmqpTemplate amqpTemplate

    int start = 0;

    @Scheduled(fixedDelay = 1000L, initialDelay = 500L)
    String sendAndReceive(String jmsChannel, String json) {
        System.out.println(" [x] Requesting fib(" + start + ")");
        String response = (String) amqpTemplate.convertSendAndReceive(jmsChannel, "login", json);
        System.out.println(" [.] Got '" + response + "'");
    }


    String send(String json) {
        amqpTemplate.convertAndSend(json)
        "success"
    }
}
