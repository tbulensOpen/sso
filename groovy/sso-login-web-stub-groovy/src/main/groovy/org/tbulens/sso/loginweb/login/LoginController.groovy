package org.tbulens.sso.loginweb.login

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
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
import org.tbulens.sso.client.login.User
import org.tbulens.sso.common.jwt.JwtUtil
import org.tbulens.sso.common.util.SsoCookieCreator
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
public class LoginController {
    @Autowired LoginSender loginSender
    @Autowired LoginRequestFactory loginRequestFactory
    @Autowired LoginValidator loginValidator
    @Autowired UserRepository userRepository
    @Value('{sso.jwt.signingKey') String signingKey
    JwtUtil jwtUtil = new JwtUtil()

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

        if (!isValidUser(loginForm, isValid)) {
            loginForm.clear()
            model.addAttribute("loginErrorMsg", "Wrong user or password")
            return "login"
        }

        LoginResponse loginResponse = processloginRequest(request, serviceUrl, user)

        loginForm.clear()
        if (!loginResponse.isLoggedIn()) {
            model.addAttribute("loginErrorMsg", loginResponse.getStatusMessage())
            return "login"
        }

        response.sendRedirect(processSuccessfulLogin(response, loginResponse))
    }

    private boolean isValidUser(LoginForm loginForm, boolean validLoginFormRequest) {
        User user = null
        if (validLoginFormRequest) {
            user = userRepository.findUser(loginForm.username, loginForm.password)
        }
        user
    }

    private LoginResponse processloginRequest(HttpServletRequest request, String serviceUrl, User user) {
        LoginRequest loginRequest = loginRequestFactory.create(request, serviceUrl, user)
        loginSender.send(loginRequest)
    }

    private String processSuccessfulLogin(HttpServletResponse response, LoginResponse loginResponse) {
        String token = jwtUtil.generateToken(signingKey, loginResponse.ssoJwtToken.toJson())
        ssoCookieCreator.create(response, token, cookieDomain, cookieContextRoot)


        loginResponse.originalServiceUrl
    }
}
