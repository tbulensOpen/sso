package org.tbulens.sso.client.login

import groovy.json.JsonSlurper


class LoginResponseFactory {

    LoginResponse create(String loginResponseJson) {
        def loginResponseMap = new JsonSlurper().parseText(loginResponseJson)
        LoginResponse loginResponse = new LoginResponse()

        loginResponse.ssoJwtToken = loginResponseMap.ssoJwtToken
        loginResponse.originalServiceUrl = loginResponseMap.originalServiceUrl
        loginResponse.statusId = loginResponseMap.statusId
        loginResponse
    }
}
