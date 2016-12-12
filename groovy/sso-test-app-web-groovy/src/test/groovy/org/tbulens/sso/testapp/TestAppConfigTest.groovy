package org.tbulens.sso.testapp

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations=["classpath:contexts/testapp-application-context.xml"])
class TestAppConfigTest {
    @Autowired TestAppConfig testAppConfig

    @Test
    void ssoFilterRegistration() {
       assert testAppConfig
    }
}
