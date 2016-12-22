package org.tbulens.sso.testapp.hello

import org.apache.log4j.Logger
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.tbulens.sso.client.login.SsoAuthentication
import javax.servlet.http.HttpServletRequest

@Controller
public class HelloController {
    Logger log = Logger.getLogger(this.class.name)

    @RequestMapping("/hello")
    public String index() {
        "hello"
    }

    @RequestMapping("/secure/securePage1")
    public String securePage1(Model model, HttpServletRequest request) {
        log.debug("Entering securePage1 controller!!!! ")

//        if (authentication) {
//            log.debug("Has authentication !!!! ${authentication.name} - ${authentication.authorities.join(",")}")
//            model.addAttribute("username", authentication.name)
//            model.addAttribute("authorities", authentication.authorities.join(","))
//        }
//        else {
//            log.debug("****** Hello Controller: No authorization object found. !!!! ************")
//        }
        "securePage1"
    }

    @RequestMapping("/secure/securePage2")
    public String securePage2() {
        "securePage2"
    }

    String authoritiesList(Collection<? extends GrantedAuthority> authorities) {
        List<String> roles = authorities.each { it.getAuthority() }
        roles.join(",")
    }

}
