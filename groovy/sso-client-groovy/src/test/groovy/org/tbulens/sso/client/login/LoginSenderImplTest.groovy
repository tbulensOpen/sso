package org.tbulens.sso.client.login

import org.gmock.WithGMock
import org.junit.Before
import org.junit.Test
import org.tbulens.sso.rabbitmq.SsoJmsMessageSender

@WithGMock
class LoginSenderImplTest {
    SsoJmsMessageSender mockSsoJmsMessageSender
    LoginSender loginSender
    LoginResponse expectedLoginResponse

    @Before
    void setUp() {
        mockSsoJmsMessageSender = mock(SsoJmsMessageSender)
        loginSender = new LoginSenderImpl(loginQueueChannel: "myqueue")
        loginSender.ssoJmsMessageSender = mockSsoJmsMessageSender
    }

    @Test
    void send() {
        LoginRequest loginRequest = new LoginRequestBuilder().build()
        String json = loginRequest.toJson()
        expectedLoginResponse = new LoginResponseBuilder().build()

        mockSsoJmsMessageSender.sendAndReceive(loginSender.loginQueueChannel, "login.rpc.requests", json).returns(expectedLoginResponse.toJson())

        play {
            assert loginSender.send(loginRequest) == expectedLoginResponse
        }
    }
}
