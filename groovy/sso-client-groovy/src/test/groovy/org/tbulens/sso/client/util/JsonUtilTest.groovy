package org.tbulens.sso.client.util

import org.junit.Before
import org.junit.Test
import org.tbulens.sso.client.authenticate.AuthenticateResponse
import org.tbulens.sso.client.authenticate.AuthenticateResponseBuilder


class JsonUtilTest {
    JsonUtil jsonUtil = new JsonUtil()
    AuthenticateResponse response

    @Before
    void setUp() {
        response = new AuthenticateResponseBuilder().build()
    }

    @Test
    void fromJson() {
        String json = jsonUtil.toJson(response)
        Map<String, Object> obj = jsonUtil.fromJson(json, Map.class)

        assert obj["secureCookieId"] == response.secureCookieId
    }
}
