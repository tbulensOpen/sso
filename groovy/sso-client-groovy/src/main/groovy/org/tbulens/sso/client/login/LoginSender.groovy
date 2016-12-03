package org.tbulens.sso.client.login

interface LoginSender {

    LoginResponse send(LoginRequest request)
}
