package org.tbulens.sso.server.util

import java.text.SimpleDateFormat


class DateConverter {

    static Date convertFromJson(String date) {
        date ? new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(date) : null
    }
}
