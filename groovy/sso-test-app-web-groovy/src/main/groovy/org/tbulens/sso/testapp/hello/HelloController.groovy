package org.tbulens.sso.testapp.hello

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
    
    @RequestMapping("/hello")
    public String index() {
        "hello"
    }

    @RequestMapping("/secure/securePage1")
    public String securePage1() {
        "securePage1"
    }

    @RequestMapping("/secure/securePage2")
    public String securePage2() {
        "securePage2"
    }

}
