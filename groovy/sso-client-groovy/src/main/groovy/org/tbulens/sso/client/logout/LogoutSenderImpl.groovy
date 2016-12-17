package org.tbulens.sso.client.logout

import org.springframework.amqp.core.AmqpTemplate


class LogoutSenderImpl implements LogoutSender {
    String logoutQueueChannel
    String logoutQueue
    AmqpTemplate amqpTemplate

    void send(LogoutRequest logoutRequest) {
        amqpTemplate.convertAndSend(logoutQueueChannel, logoutQueue, logoutRequest.toJson())

    }
}
