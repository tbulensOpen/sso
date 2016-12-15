package org.tbulens.sso.client.login

import groovy.json.JsonSlurper


class LoginResponseFactory {

    LoginResponse create(String loginResponseJson) {
        def loginResponseMap = new JsonSlurper().parseText(loginResponseJson)
        LoginResponse loginResponse = new LoginResponse()
        loginResponse.requestTicket = loginResponseMap.requestTicket
        loginResponse.originalServiceUrl = loginResponseMap.originalServiceUrl
        loginResponse.secureCookieId = loginResponseMap.secureCookieId
        loginResponse.statusId = loginResponseMap.statusId
        loginResponse.userId = loginResponseMap.userId
        loginResponse
    }
}
