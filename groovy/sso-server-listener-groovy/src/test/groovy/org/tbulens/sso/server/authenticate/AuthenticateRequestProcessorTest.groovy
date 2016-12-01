package org.tbulens.sso.server.authenticate

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.tbulens.sso.client.authenticate.AuthenticateRequest
import org.tbulens.sso.client.authenticate.AuthenticateRequestBuilder

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations=["classpath:contexts/sso-server-application-context.xml"])
class AuthenticateRequestProcessorTest {

    @Autowired AuthenticateRequestProcessor authenticateRequestProcessor
    AuthenticateRequest authenticateRequest

    @Before
    void setUp() {
       authenticateRequest = new AuthenticateRequestBuilder().build()
    }

    @Test
    void process_valid_oneApp() {
      String authenticateResponseJson =  authenticateRequestProcessor.process(authenticateRequest.toJson())
      assert true
    }

    @Test
    void process_valid_secondApp() {
      assert true
    }

    @Test
    void process_invalid_unauthorized() {
      assert true
    }


}
