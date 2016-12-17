package org.tbulens.sso.logout

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class LogoutController {

    @RequestMapping(value = '/logout', method = RequestMethod.GET)
    String logout(@RequestParam("loginUrl") String loginUrl, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        request.session.invalidate()
        model.addAttribute("loginUrl", loginUrl)
        "logout"
    }
}
