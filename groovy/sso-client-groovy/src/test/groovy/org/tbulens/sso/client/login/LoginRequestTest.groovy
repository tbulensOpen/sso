package org.tbulens.sso.client.login

import org.junit.Test
import org.tbulens.sso.client.login.LoginRequestBuilder
import org.tbulens.sso.client.util.JsonUtil


class LoginRequestTest {

    @Test
    void toJson() {
        String json = new LoginRequestBuilder().build().toJson()
        assert new JsonUtil().clean(json) == '{"userId":"userA","originalServiceUrl":"http://localhost:8080/testapp","authorities":[]}'
    }
}
