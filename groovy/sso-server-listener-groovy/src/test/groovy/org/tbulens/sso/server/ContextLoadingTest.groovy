package org.tbulens.sso.server

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.support.GenericXmlApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.tbulens.sso.server.login.LoginRequestProcessor

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations=["classpath:contexts/sso-server-application-context.xml"])
class ContextLoadingTest {

    @Autowired LoginRequestProcessor loginRequestProcessor

    @Test
    void load() {
         assert loginRequestProcessor
    }
}
