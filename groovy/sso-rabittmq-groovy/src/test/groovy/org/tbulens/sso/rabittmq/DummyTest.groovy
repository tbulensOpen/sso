package org.tbulens.sso.rabittmq

import org.junit.Test
import org.tbulens.sso.rabittmq.Dummy


class DummyTest {

    @Test
    void hello() {
       assert "Hi, there!" == new Dummy().hello()
    }
}
