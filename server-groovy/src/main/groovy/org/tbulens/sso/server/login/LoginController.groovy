package org.tbulens.sso.server.login

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    String login(HttpServletRequest request, HttpServletResponse response) {
        response.sendRedirect("http://localhost:8082/sso/login")
        null
    }
}
