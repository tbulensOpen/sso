package org.tbulens.sso.client.login

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations=["classpath:contexts/test-client-context.xml"])
class LoginSenderImplTest {
    @Autowired LoginSender loginSender

    @Before
    void setUp() {

    }

    @Ignore
    @Test
    void send() {
        LoginRequest loginRequest = new LoginRequestBuilder().build()
        LoginResponse loginResponse = loginSender.send(loginRequest)

        assert loginResponse
    }
}
