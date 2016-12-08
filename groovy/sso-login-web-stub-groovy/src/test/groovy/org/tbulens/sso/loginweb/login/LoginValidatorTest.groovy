package org.tbulens.sso.loginweb.login

import org.junit.Before
import org.junit.Test
import org.springframework.validation.Errors
import org.springframework.validation.MapBindingResult


class LoginValidatorTest {
    private LoginValidator validator
    LoginForm loginForm
    Errors errors = new MapBindingResult([:], '')

    // todo add much more test coverage!!!!!

    @Before
    void setUp() {
        validator = new LoginValidator()
        loginForm = new LoginForm()
        errors = new MapBindingResult([:], '')
    }

    @Test
    void validate_validUserNamePassword() {
        loginForm.username = '12345678'
        loginForm.password = 'a!D2vbhuf'
        assert validator.validate(loginForm, errors)
    }

    @Test
    void validate_invalidUserName_space() {
        loginForm.username = '123 456'
        loginForm.password = 'A!b1dddd'
       assert !validator.validate(loginForm, errors)
    }
}
