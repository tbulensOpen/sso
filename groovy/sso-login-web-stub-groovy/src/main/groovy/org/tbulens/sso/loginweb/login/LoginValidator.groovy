package org.tbulens.sso.loginweb.login

import org.springframework.stereotype.Component
import org.springframework.validation.Errors


@Component
class LoginValidator {

/* Username Policy:
   a-z, A-Z, 0-9, max 15 chars, min 6 chars
*/

/*  Password Policy:
    At least 8 chars
    Contains at least one digit
    Contains at least one lower alpha char and one upper alpha char
    Contains at least one char within a set of special chars (@#%$^ etc.)
    Does not contain space, tab, etc.
*/

    String usernamePattern = "^[a-zA-Z0-9_-]{6,15}(^[^S])*\$"
    String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[\$@\$!%*#?&])[A-Za-z\\d\$@\$!%*#?&]{8,20}\$"

    protected boolean validate(LoginForm loginForm, Errors errors) {
        boolean valid = false
        if (loginForm.username.matches(usernamePattern)) valid = true
        if (valid && !loginForm.password.matches(passwordPattern)) valid = false

        if (!valid) {
            errors.reject("exception.credentials.invalid", "Credentials entered is invalid.")
        }
        valid
    }
}
