package org.tbulens.sso.server

import org.springframework.amqp.rabbit.annotation.RabbitListener


class LoginRequestProcessor {

    @RabbitListener(queues = "tut.rpc.requests")
    // @SendTo("tut.rpc.replies") used when the client doesn't set replyTo.
    String loging(String loginRequestJson) {
        System.out.println(" [x] Received request for " + loginRequestJson)
        "requestTicket0001"
    }
}
