package org.tbulens.sso.filter

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class SsoFilter implements Filter {
    String ssoUrl

    void init(FilterConfig filterConfig) throws ServletException {

    }

    // be sure only secure urls get into this filter by specifying filter pattern.
    void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String encodedService = buildEncodedServiceUrl(servletRequest)

        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.sendRedirect(ssoUrl + "?service=" + encodedService);

    }

    private String buildEncodedServiceUrl(ServletRequest servletRequest) {
        String url = ((HttpServletRequest) servletRequest).getRequestURL().toString();
        String queryString = ((HttpServletRequest) servletRequest).getQueryString();
        URLEncoder.encode(url + "?" + queryString, "UTF-16")
    }

    void destroy() {

    }
}
