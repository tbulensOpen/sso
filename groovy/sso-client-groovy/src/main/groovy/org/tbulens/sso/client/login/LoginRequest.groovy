package org.tbulens.sso.client.login

import groovy.transform.Canonical
import org.tbulens.sso.client.util.JsonUtil

@Canonical
class LoginRequest implements Serializable {
    String userId
    String originalServiceUrl

    String toJson() {
        new JsonUtil().toJson(this)
    }
}
