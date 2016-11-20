package org.tbulens.sso.rabbitmq

import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.jms.admin.RMQConnectionFactory
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.core.MessageCreator
import org.springframework.stereotype.Component

@EnableJms
@Component
class SsoJmsMessageSender {
    @Autowired  AmqpTemplate amqpTemplate

    String send(String jmsChannel, String json) {
        amqpTemplate.convertSendAndReceive(jmsChannel, json) as String
    }
}
