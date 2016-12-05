package org.tbulens.sso.client.authenticate

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations=["classpath:contexts/test-client-context.xml"])
class AuthenticateSenderImplTest {

    @Autowired AuthenticateSender authenticateSender
    AuthenticateRequest authenticateRequest

    @Before
    void setUp() {
        authenticateRequest = new AuthenticateRequestBuilder().build()
    }

    @Ignore
    @Test
    void send() {
        AuthenticateResponse response = authenticateSender.send(authenticateRequest)
        assert response
    }
}
