package org.tbulens.sso.loginweb.login

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.ModelAttribute
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
    @Autowired CredentialFactory credentialFactory
    @Autowired LoginValidator loginValidator
    String cookieDomain = 'localhost'
    String cookieContextRoot = '/'
    SsoCookieCreator ssoCookieCreator = new SsoCookieCreator()
    Logger logger = Logger.getLogger(this.class.name)

    @RequestMapping(value = '/login', method = RequestMethod.GET)
    String login(HttpServletRequest request, ModelMap model) {
        String queryString = request.getQueryString()

        if (queryString) {
            String encodedServiceUrl = queryString.substring(8)
            String serviceUrl = URLDecoder.decode(encodedServiceUrl, "UTF-16")
            model.addAttribute("serviceUrl", serviceUrl)
        }
        "login"
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    String login(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                 @ModelAttribute("loginForm") LoginForm loginForm, Errors errors) {

        boolean isValid = loginValidator.validate(loginForm, errors)

        String serviceUrl = request.getParameter("serviceUrl")
        logger.debug( "${loginForm.username} - ${loginForm.password}")
        logger.debug("is valid = ${isValid} +  serviceUrl = " + serviceUrl)

        if (!isValid) {
            loginForm.clear()
            model.addAttribute("loginErrorMsg", "Wrong user or password")
            return "login"
        }

        LoginResponse loginResponse = processloginRequest(request, serviceUrl)

        if (!loginResponse.isLoggedIn()) {
            model.addAttribute("loginErrorMsg", loginResponse.getStatusMessage())
            return "login"
        }

        response.sendRedirect(processSuccessfulLogin(loginForm, response, loginResponse))
    }

    private String processSuccessfulLogin(LoginForm loginForm, HttpServletResponse response, LoginResponse loginResponse) {
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_TESTAPP")
        credentialFactory.setAuthentication(loginForm.username, [authority])
        ssoCookieCreator.create(response, loginResponse.secureCookieId, cookieDomain, cookieContextRoot)

        loginForm.clear()
        loginResponse.originalServiceUrl
    }

    private LoginResponse processloginRequest(HttpServletRequest request, String serviceUrl) {
        LoginRequest loginRequest = loginRequestFactory.create(request, serviceUrl)
        loginSender.send(loginRequest)
    }
}
