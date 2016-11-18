package org.tbulens.sso.server.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.tbulens.sso.server.model.RequestTicket

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
class SsoController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    RequestTicket login(HttpServletRequest request, HttpServletResponse response) {
        null
    }

    @RequestMapping(value = "/isAuthenticated", method = RequestMethod.GET)
    RequestTicket isAuthenticated(HttpServletRequest request, HttpServletResponse response) {
        null
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    boolean logout(HttpServletRequest request, HttpServletResponse response) {
        null
    }

}
