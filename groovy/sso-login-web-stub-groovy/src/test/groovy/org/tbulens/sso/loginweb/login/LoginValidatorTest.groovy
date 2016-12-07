package org.tbulens.sso.loginweb.login

import org.junit.Before
import org.junit.Test


class LoginValidatorTest {
    private LoginValidator validator

    // todo add much more test coverage!!!!!

    @Before
    void setUp() {
        validator = new LoginValidator()
    }

    @Test
    void validate_validUserNamePassword() {
        assert validator.validate("123456", "A#b1dddd")
    }

    @Test
    void validate_invalidUserName_space() {
       assert !validator.validate("123 456", "A!b1dddd")

    }
}
