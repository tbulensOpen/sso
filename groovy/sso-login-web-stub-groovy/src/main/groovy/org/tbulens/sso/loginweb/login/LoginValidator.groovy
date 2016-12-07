package org.tbulens.sso.loginweb.login

import org.springframework.stereotype.Component


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
    String passwordPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s)(?=.*[@#\$%^&+=]).{8,20}\$"

    protected boolean validate(String username, String password) {
        if (!username.matches(usernamePattern)) return false
        if (!password.matches(passwordPattern)) return false
//        if (password.contains(" ")) return false
        true
    }
}
