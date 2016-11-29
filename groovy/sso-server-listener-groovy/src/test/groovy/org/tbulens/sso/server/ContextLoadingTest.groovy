package org.tbulens.sso.server

import org.junit.Before
import org.junit.Test
import org.springframework.context.ApplicationContext
import org.springframework.context.support.GenericXmlApplicationContext
import org.tbulens.sso.client.login.LoginRequest


class ContextLoadingTest {

    @Test
    void load() {
        ApplicationContext context =
                new GenericXmlApplicationContext("classpath:/contexts/sso-server-application-context.xml");
        assert context.getBean("loginRequestProcessor", LoginRequestProcessor)
    }
}
