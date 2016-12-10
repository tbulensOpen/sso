package org.tbulens.sso.client.login

import org.junit.Ignore
import org.junit.Test
import org.tbulens.sso.client.util.JsonUtil


class LoginResponseTest {

    @Ignore
    @Test
    void toJson() {
        String json = new LoginResponseBuilder().build().toJson()
        Map<String, String> map = new JsonUtil().fromJson(json)

        assert !map.get("BAD_REQUEST")
    }
}
