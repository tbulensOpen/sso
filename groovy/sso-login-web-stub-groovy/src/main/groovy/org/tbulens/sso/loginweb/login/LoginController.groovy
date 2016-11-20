package org.tbulens.sso.loginweb.login

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.tbulens.sso.client.login.LoginRequest

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
public class LoginController {
    @Autowired LoginSsoSender loginSsoSender
    @Autowired LoginRequestFactory loginRequestFactory

    @RequestMapping(value = '/secure/login', method = RequestMethod.GET)
    String login() {
        "login"
    }

    @RequestMapping(value = "/secure/login", method = RequestMethod.POST)
    String login(HttpServletRequest request, HttpServletResponse response) {
        LoginRequest loginRequest = loginRequestFactory.create(request)
        String requestTicket = loginSsoSender.send(loginRequest)
        return "Greetings from Spring Boot!";
    }
    
}
