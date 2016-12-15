package org.tbulens.sso.common.util

import javax.servlet.http.HttpServletRequest

class UrlBuilder {

    String create(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        queryString ? url + "?" + queryString : url
    }
}
