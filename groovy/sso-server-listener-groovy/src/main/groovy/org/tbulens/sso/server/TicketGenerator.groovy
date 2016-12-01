package org.tbulens.sso.server


class TicketGenerator {

    String generateRequestTicket() {
        "RT_" + generateTicket()
    }

    String generateTicket() {
        UUID requestTicketUid = UUID.randomUUID()
        String.valueOf(requestTicketUid)
    }
}
