package org.tbulens.sso.loginweb.login

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.tbulens.sso.client.login.LoginRequest
import org.tbulens.sso.client.login.LoginRequestFactory
import org.tbulens.sso.client.login.LoginResponse
import org.tbulens.sso.client.login.LoginSender
import org.tbulens.sso.common.util.SsoCookieCreator
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
public class LoginController {
    @Autowired LoginSender loginSender
    @Autowired LoginRequestFactory loginRequestFactory
    @Value("{cookie.domain}") String cookieDomain
    SsoCookieCreator ssoCookieCreator = new SsoCookieCreator()

    @RequestMapping(value = '/login', method = RequestMethod.GET)
    String login() {
        "login"
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    String login(HttpServletRequest request, HttpServletResponse response) {
        LoginRequest loginRequest = loginRequestFactory.create(request)
        LoginResponse loginResponse = loginSender.send(loginRequest)
        ssoCookieCreator.create(response, loginResponse.secureCookieId, cookieDomain)
        return "Greetings from Spring Boot!";
    }
    
}
