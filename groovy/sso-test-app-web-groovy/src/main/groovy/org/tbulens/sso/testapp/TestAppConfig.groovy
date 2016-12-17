package org.tbulens.sso.testapp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.tbulens.sso.filter.SsoFilter
import org.tbulens.sso.filter.SsoLogoutFilter

@Component
class TestAppConfig {
    @Autowired SsoFilter ssoFilter
    @Autowired SsoLogoutFilter ssoLogoutFilter

    @Bean
    public FilterRegistrationBean ssoFilterRegistration() {
        FilterRegistrationBean registration = new  FilterRegistrationBean();
        registration.setFilter(ssoFilter);
        registration.setOrder(1);
        registration.addUrlPatterns("/secure/*")
        return registration;
    }

    @Bean
    public FilterRegistrationBean ssoLogoutFilterRegistration() {
        FilterRegistrationBean registration = new  FilterRegistrationBean();
        registration.setFilter(ssoLogoutFilter);
        registration.setOrder(2);
        registration.addUrlPatterns("/logout")
        return registration;
    }
}
