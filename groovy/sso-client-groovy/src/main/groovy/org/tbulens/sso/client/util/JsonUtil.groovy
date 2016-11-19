package org.tbulens.sso.client.util

import groovy.json.JsonBuilder

class JsonUtil {

    String clean(String json) {
        json.replaceAll("\n", "").replaceAll(" ", "")
    }

    String toJson(Object o) {
        new JsonBuilder(o).toPrettyString()
    }
}
