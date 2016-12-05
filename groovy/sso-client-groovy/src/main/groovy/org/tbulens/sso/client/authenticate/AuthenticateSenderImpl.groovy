package org.tbulens.sso.client.authenticate

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AuthenticateSenderImpl implements AuthenticateSender {

    String authenticateQueueChannel = "sso.rpc"
    String authenticateQueue = "authenticate"
    AuthResponseFactory authResponseFactory = new AuthResponseFactory()
    @Autowired AmqpTemplate amqpTemplate

    AuthenticateResponse send(AuthenticateRequest request) {
        String authenticateResponseJson = sendAndReceive(authenticateQueueChannel, authenticateQueue, request.toJson())
        authResponseFactory.create(authenticateResponseJson)
    }

    private String sendAndReceive(String jmsChannel, String route, String json) {
        String response = (String) amqpTemplate.convertSendAndReceive(jmsChannel, route, json);
        System.out.println(" [.] Got '" + response + "'");
        response
    }
}
