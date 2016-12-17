package org.tbulens.sso.client.logout

import org.tbulens.sso.client.util.JsonUtil


class LogoutRequest implements Serializable {
    String secureCookieId


    String toJson() {
        new JsonUtil().toJson(this)
    }
}
