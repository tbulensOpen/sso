package org.tbulens.sso.client.authenticate

import org.tbulens.sso.rabbitmq.SsoJmsMessageSender

class AuthenticateSenderImpl implements AuthenticateSender {

    String authenticateQueueChannel
    SsoJmsMessageSender ssoJmsMessageSender
    AuthResponseFactory authResponseFactory = new AuthResponseFactory()

    AuthenticateResponse send(AuthenticateRequest request) {
        String authenticateResponseJson = ssoJmsMessageSender.sendAndReceive(authenticateQueueChannel, "authenticate.rpc.requests", request.toJson())
        authResponseFactory.create(authenticateResponseJson)
    }
}
