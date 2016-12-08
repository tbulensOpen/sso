package org.tbulens.sso.loginweb.login


class LoginForm implements Serializable {
    String username
    String password

    void clear() {
        username = ""
        password = ""
    }
}
