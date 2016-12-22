package org.tbulens.sso.client

import groovy.transform.Canonical
import org.tbulens.sso.client.util.JsonUtil

@Canonical
class SsoJwtToken implements Serializable {
    String secureCookieId
    String userId
    String requestTicket
    List<String> authorities = []

    String toJson() {
        new JsonUtil().toJson(this)
    }
}
