package org.tbulens.sso.client.authenticate

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

class AuthenticateSenderImpl implements AuthenticateSender {

    String authenticateQueueChannel
    String authenticateQueue
    AuthResponseFactory authResponseFactory = new AuthResponseFactory()
    AmqpTemplate amqpTemplate

    AuthenticateResponse send(AuthenticateRequest request) {
        String authenticateResponseJson = sendAndReceive(authenticateQueueChannel, authenticateQueue, request.toJson())
        authResponseFactory.create(authenticateResponseJson)
    }

    private String sendAndReceive(String jmsChannel, String route, String json) {
        String response = (String) amqpTemplate.convertSendAndReceive(jmsChannel, route, json);
        System.out.println(" [.] AuthenticateSender Response: '" + response + "'");
        response
    }
}
