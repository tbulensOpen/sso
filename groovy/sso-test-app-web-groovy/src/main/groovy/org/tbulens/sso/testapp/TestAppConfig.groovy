package org.tbulens.sso.testapp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.tbulens.sso.filter.SsoFilter

@Component
class TestAppConfig {
    @Autowired SsoFilter ssoFilter

    @Bean
    public FilterRegistrationBean ssoFilterRegistration() {
        FilterRegistrationBean registration = new  FilterRegistrationBean();
        registration.setFilter(ssoFilter);
        registration.setOrder(1);
        return registration;
    }
}
