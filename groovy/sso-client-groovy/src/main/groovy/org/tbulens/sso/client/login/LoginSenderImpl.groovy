package org.tbulens.sso.client.login

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired


class LoginSenderImpl implements LoginSender {

    String loginQueueChannel = "sso.rpc"
    String loginQueue = "login"
    LoginResponseFactory loginResponseFactory = new LoginResponseFactory()
    @Autowired AmqpTemplate amqpTemplate

    LoginResponse send(LoginRequest request) {
        String loginResponseJson = sendAndReceive(loginQueueChannel, loginQueue, request.toJson())
        loginResponseFactory.create(loginResponseJson)
    }

    private String sendAndReceive(String jmsChannel, String route, String json) {
        String response = (String) amqpTemplate.convertSendAndReceive(jmsChannel, route, json);
        System.out.println(" [.] Got '" + response + "'");
        response
    }
}
