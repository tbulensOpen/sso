package org.tbulens.sso.client.util

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

class JsonUtil {
    ObjectMapper objectMapper = new ObjectMapper()
    JsonSlurper jsonSlurper = new JsonSlurper()

    String clean(String json) {
        json.replaceAll("\n", "").replaceAll(" ", "")
    }

    String toJson(Object o) {
        new JsonBuilder(o).toPrettyString()
    }

    def fromJson(String json) {
        jsonSlurper.parseText(json)
    }
//
//    T get(String content, Class clazz) throws IOException {
//        return (T) objectMapper.readValue(content, clazz);
//    }
}
