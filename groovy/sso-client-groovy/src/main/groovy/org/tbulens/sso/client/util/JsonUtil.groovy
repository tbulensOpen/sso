package org.tbulens.sso.client.util

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.springframework.stereotype.Component

class JsonUtil {

    String clean(String json) {
        json.replaceAll("\n", "").replaceAll(" ", "")
    }

    String toJson(Object o) {
        new JsonBuilder(o).toPrettyString()
    }

    def fromJson(String json) {
        def jsonSlurper = new JsonSlurper()
        jsonSlurper.parseText(json)
    }
}
