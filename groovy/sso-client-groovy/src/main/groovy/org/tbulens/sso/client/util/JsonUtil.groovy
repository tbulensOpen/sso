package org.tbulens.sso.client.util

import com.fasterxml.jackson.databind.ObjectMapper

class JsonUtil {
    ObjectMapper objectMapper = new ObjectMapper()

    String clean(String json) {
        json.replaceAll("\n", "").replaceAll(" ", "")
    }

    String toJson(Object o) {
        objectMapper.writeValueAsString(o)
    }

    def fromJson(String json, Class clazz) {
        objectMapper.readValue(json, clazz)
    }
}
