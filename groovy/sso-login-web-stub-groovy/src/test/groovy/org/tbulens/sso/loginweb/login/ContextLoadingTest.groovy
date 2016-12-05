package org.tbulens.sso.loginweb.login

import org.junit.Test
import org.springframework.context.ApplicationContext
import org.springframework.context.support.GenericXmlApplicationContext


class ContextLoadingTest {

    @Test
    void loadContext() {
        ApplicationContext context =
                new GenericXmlApplicationContext("classpath:/contexts/application-context.xml");
        assert context.getBean("loginSender")
    }
}
