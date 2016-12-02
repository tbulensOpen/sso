package org.tbulens.sso.rabbitmq

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jms.annotation.EnableJms
import org.springframework.stereotype.Component

@EnableJms
@Component
class SsoJmsMessageSender {
    @Autowired  AmqpTemplate amqpTemplate

    String sendAndReceive(String jmsChannel, String route, String json) {
        String response = (String) amqpTemplate.convertSendAndReceive(jmsChannel, route, json);
        System.out.println(" [.] Got '" + response + "'");
        response
    }
    
    String send(String json) {
        amqpTemplate.convertAndSend(json)
        "success"
    }
}
