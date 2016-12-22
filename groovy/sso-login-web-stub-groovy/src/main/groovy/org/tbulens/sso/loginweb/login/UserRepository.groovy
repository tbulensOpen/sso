package org.tbulens.sso.loginweb.login

import org.springframework.stereotype.Repository
import org.tbulens.sso.client.login.User

@Repository
class UserRepository {

    User findUser(String userId, String password) {
        new User(userId: userId, authorities: ['TEST_APP'])
    }
}
