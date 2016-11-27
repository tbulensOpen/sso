package org.tbulens.sso.rabbitmq

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.springframework.context.ApplicationContext
import org.springframework.context.support.GenericXmlApplicationContext


class SsoJmsMessageSenderTest {
    static final String EXPECTED_MSG = "someJsonString"
    static final String CHANNEL = "myqueue"
    SsoJmsMessageSender sender

    @Before
    void setUp() {
        ApplicationContext context =
                new GenericXmlApplicationContext("classpath:/contexts/rabbit-context.xml");
        sender = context.getBean("ssoJmsMessageSender")
    }

    @Ignore
    @Test
    void send() {
       String actualMsg = sender.send("someString")
        assert actualMsg == EXPECTED_MSG
    }
}
