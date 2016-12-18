package org.tbulens.sso.logout

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.tbulens.sso.client.logout.LogoutRequest
import org.tbulens.sso.client.logout.LogoutSender
import org.tbulens.sso.common.util.SsoCookieCreator
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class LogoutController {
    @Autowired LogoutSender logoutSender
    Logger log = Logger.getLogger(this.class.name)

    @RequestMapping(value = '/logout', method = RequestMethod.GET)
    String logout(@RequestParam("loginUrl") String encodedLoginUrl, ModelMap model, HttpServletRequest request) {
        log.debug("**** Logout: EncodedUrl = " + encodedLoginUrl)
        Cookie cookie = findSecureCookie(request.cookies)

        if (cookie) {
            cookie.setMaxAge(0)  // expire cookie
            LogoutRequest logoutRequest = new LogoutRequest(secureCookieId: cookie.value)
            logoutSender.send(logoutRequest)
        }

        request.session.invalidate()
        String decodedLoginUrl = URLDecoder.decode(encodedLoginUrl, "UTF-16")
        model.addAttribute("loginUrl", decodedLoginUrl)
        "logout"
    }

    @RequestMapping(value = '/login', method = RequestMethod.GET)
    void login(@RequestParam("loginUrl") String encodedLoginUrl, HttpServletRequest request, HttpServletResponse response) {
        String decodedLoginUrl = URLDecoder.decode(encodedLoginUrl, "UTF-16")
        log.debug("Logout - loginUrl redirect = ${decodedLoginUrl} - ${encodedLoginUrl}")
        response.sendRedirect(decodedLoginUrl)
    }

    private Cookie findSecureCookie(Cookie[] cookies) {
        cookies.find { it.name == SsoCookieCreator.SSO_SESSION_ID }
    }
}

