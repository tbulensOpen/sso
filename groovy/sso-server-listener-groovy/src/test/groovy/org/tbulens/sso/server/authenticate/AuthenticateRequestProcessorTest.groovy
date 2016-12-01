package org.tbulens.sso.server.authenticate

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations=["classpath:contexts/sso-server-application-context.xml"])
class AuthenticateRequestProcessorTest {

    @Autowired AuthenticateRequestProcessor authenticateRequestProcessor


    @Before
    void setUp() {

    }

    @Test
    void process() {
      assert true
    }
}
