package org.tbulens.sso.client.logout


interface LogoutSender {

    void send(LogoutRequest logoutRequest)

}