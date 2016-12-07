package org.tbulens.sso.loginweb.login

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView
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
    @Autowired CredentialFactory credentialFactory
    @Autowired LoginValidator loginValidator
    @Value("{cookie.domain}") String cookieDomain
    @Value("{cookie.context.rool}") String cookieContextRoot
    SsoCookieCreator ssoCookieCreator = new SsoCookieCreator()

    @RequestMapping(value = '/login', method = RequestMethod.GET)
    String login(HttpServletRequest request, ModelMap model) {
        String encodedServiceUrl = request.getParameter("service")
        model.put("service", encodedServiceUrl)
        "login"
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    String login(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("username")
        String password = request.getParameter("password")

        boolean isValid = loginValidator.validate(userName, password)

        if (!isValid) return "Bad username and password"

        LoginRequest loginRequest = loginRequestFactory.create(request)
        LoginResponse loginResponse = loginSender.send(loginRequest)

        if (loginResponse.isLoggedIn()) {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_TESTAPP")
            credentialFactory.setAuthentication(userName,[authority])
            ssoCookieCreator.create(response, loginResponse.secureCookieId, cookieDomain, cookieContextRoot)
        }
        return "Greetings from Spring Boot!";
    }
    
}
