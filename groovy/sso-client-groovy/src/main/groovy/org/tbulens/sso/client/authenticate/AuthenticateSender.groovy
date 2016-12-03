package org.tbulens.sso.client.authenticate


interface AuthenticateSender {
    AuthenticateResponse send(AuthenticateRequest request)
}
