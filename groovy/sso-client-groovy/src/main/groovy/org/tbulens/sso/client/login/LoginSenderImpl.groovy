package org.tbulens.sso.client.login

import org.springframework.amqp.core.AmqpTemplate

class LoginSenderImpl implements LoginSender {

    String loginQueueChannel
    String loginQueue
    LoginResponseFactory loginResponseFactory = new LoginResponseFactory()
    AmqpTemplate amqpTemplate

    LoginResponse send(LoginRequest request) {
        println "aaa = " + amqpTemplate + "  loginQueueChannel = " + loginQueueChannel
                String loginResponseJson = sendAndReceive(loginQueueChannel, loginQueue, request.toJson())
        loginResponseFactory.create(loginResponseJson)
    }

    private String sendAndReceive(String jmsChannel, String route, String json) {
        String response = (String) amqpTemplate.convertSendAndReceive(jmsChannel, route, json);
        System.out.println(" [.] Got '" + response + "'");
        response
    }
}
