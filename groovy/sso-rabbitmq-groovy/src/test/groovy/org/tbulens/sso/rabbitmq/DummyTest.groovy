package org.tbulens.sso.rabbitmq

import org.junit.Test
import org.tbulens.sso.rabbitmq.Dummy


class DummyTest {

    @Test
    void hello() {
       assert "Hi, there!" == new org.tbulens.sso.rabbitmq.Dummy().hello()
    }
}
