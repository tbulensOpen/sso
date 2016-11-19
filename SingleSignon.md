Ted's Single SignOn

Index:
   Objective
   Description
   Overall Architecture
   Use Cases
   Data Structures
   Ticket Responsibilities
   Recommended Technologies

Objective: 
A lightweight, single signon authentication system with minimum dependencies,
that is fast, with concurrency in mind. 

Overall Description:
Five major components:
   UserSessionTicket (UST) – contains all info about a user and user login
   RequestTicket (RT) – changes on every request, short life.
   LoginTicket (LT) – stored within UST, keeps login info
   UserId – Used to prevent multiple sessions for same user to be login.
   TokenPayload – Encapulates UST, RT, and UserId into single object to be passed to
      other services by Java Web Token (JWT) if necessary. 

    // Note: we may want to allow multiple sessions for same user??
   //            allow multiple urls for same user

When a loginTicket expires or is no longer valid, the sso filter is required to detect that.
Several alternatives I can think of:
1) Client sso filter makes http call to sso server every request
2) Design a client callback where servers notifies all clients
3) Design a  publish / subscribe model to be notified.
The design really needs to be stateless, callbacks might become an issue with clustering.
What is the tolerance level when a ticket expires or no longer valid can a session still be valid?

Overall Architecture:
3 Major Components:
1) Client – Call SSO Server per request to verify authentication / login
	Need to determine secure/insecure pages.
2) SSO Server – Ticket Management
3) Login Page / Authentication

What if sso server is not a deployable but a shared code module to be included in client applications?
The problem with that is changes to the sso server code base requires changes to all clients. Not a good option. Sso server needs to be its own deployable and clustered to allow fast changes ( security fixes ) without down time to the client applications.


Is it possible to have a rules engine injected at time of login or application start up? 
Therefore when a user logins the current security rules id is injected into the UST and client application
can apply those rules without hitting the sso server on every request, only at time of login. Or the client filter can apply the security rules per request which was injected at time of start up without hitting the sso server. 

I am going to defer the rule engine solution to a later time. It would be interesting to see if possible.

Each component can be built tested independently and what ever technology stack preferred.
SSO server is critical to support concurrency and perform well. so I recommend something like Redis in memory clusterable cache and GO for language of choice to process ticket management. 

How do we get the RT passed to every request? Do we need to set session cookie? 
Does the client filter handle this?

What about RabbitMQ Non Blocking RPC type of messages dealing with RT requests?
Need to think about concurrency, channels, queues for responses.

Slight design issue is original url that user will get redirected back to must be keep per request ticket.
Why: A user can come in multiple browser tabs one per application, the url gets redirected back needs to be per browser tab.

User Cases:
Happy Path Flow Sequence:
User navigates to secure web site with request parameters without request ticket (RT).
SSO filter receives http request, detects no RT
Redirect to sso setting service to originl url and all request parameters
UserSessionTicket (UST) is created without loginTicket ( LT)
RequestTicket is created
Stop filter chain
Navigate user to login page with RT
Invalidate RT assign new RT
User logins successfully
    // Put principle in session --- prefer not too, since principal is available in cache.
    // Spring security may require attributes to be stored in principal on the session??
Invalidate RT assign new RT
External Redirect user back to original url with request parameters

User already log in:
SSO filter receives http request, detects RT
validates RT
athenticates UST
Invalidate RT assign new RT
continues on with filter chain

RT invalid  
force logout
redirect to login page
log security event

UST invalid 
force logout
redirect to login page
log security event

User already login with different session
force logout
redirect to login page
log security event

Logout
force logout
go to logout page

Force user logout after x hours
force logout
redirect to login page
log security event

User has multiple tabs same url
force logout
redirect to login page
log security event

User has multiple tabs different applications/urls
create RT ticket for userId, URL
redirect back to client application with RT.

// JSON Data Structures

Token Payload:
RT - current
UST
UserId

// immuatable, insert every time, no updates
RequestTicket {
    requestTicketId
    httpSessionId
    entryTime
    expiredTime
    originalUrl with request params
}

UserSessionTicket {
     httpSessionId
     ipAddress
     entryDate
     LoginTicket {
	loginTime
            expirationTime
            failedAttempts
            lastLoginAttemptTime
            accountLockedTime
            attributes {
		userId
         		lastName
         		firstName
                       authorities {
                           role_1
                           role_2
                       }
         		….
	}
     }
}


// used to prevent same user from logging on from multiple sessions
UserId {
   userId
   httpSessionId
}


UserSessionTicket
     // creates place holder with no loginTicket
     UserSessionTicket create(sessionId, ipAddress, originalServiceUrl,  requestParams)
          
              
    // replace login ticket based on successful or failed login. Returns new instance, make immutable.
    // if login is successful, create UserId mapping
    UserSessionTicket login(LoginTicket loginTicket) 
      
     void remove()

     // call LoginTicket.isAuthenticated(), sessionId must match
     AthenticationResult isAthenticated(sessionId, currentTime) 

     






RequestTicket
     // returns requestTicketId, user first time only.
    String create(httpSessionId, entryTime, expiredTime)

    // All requests other than first time, retrieve current request ticket and creates new one.
    String create(requestTicketId , userSessionTicketId, entryTime, expiredTime,
                         originalUrlWithParams)

    void remove() 

    boolean isValid() 



LoginTicket
    LoginTicket createSuccessfulLogin(entryTime, expirationTime, Map attributes)

   // if exists, create new instance, make it immutable.
    LoginTicket failedLogin(entryTime)


Technologies:
Go for ticket management and interacting with Redis
Json Web Tokens ??? Securing passing token payload ??
Spring MVC for login page with thymeleaf??
     Is there something lighter that can connect to SQL, LDAP, ActiveDirectory??
     Spark?? 
Redis Clustered for storing ticket payloads


 
SsoService
    RequestTicket login(LoginRequestJson)
    RequestTicket isAuthenticated(AuthenticatedRequestJson)
    boolean logout(LogoutRequestJson)
    JWT getToken(TokenRequestJson)

LoginService
    String login(HttpServletRequest request, HttpServletResponse response)

LogoutService
      String logout(HttpServletRequest request, HttpServletResponse response)

Sequence:
	1. Client Filter
1. Original URL does not contain request ticket
1. Created encoded service url
2. Redirect to login service with encode service url
2. Original URL does contain request ticket
1. Call SSO service isAuthenticated
2. If yes
1. replace request ticket with new request ticket to request params
2. continue with filter chain
3. If no
1. Created encoded service url
2. Redirect to login service with encode service url	
	2. Login Service
1. Save encoded service url
2. Display Login page
3. Authenticate user successful 
1. Call SSO service login, return RT
2. decode service url
3. redirect user back to service url with RT ticket
4. Authenticated user not successful 
1. Attempt multiple times
2. Lock Account or Access Denied
3. Sso Service
1. Receive login request – not logged in
1. Check isAuthenticated – false
2. Create UST 
3. Create Request Ticket
4. Create UserId
5. Return RT
2. Receive login request – already logged in - valid
1. Check isAuthenticated – true
2. session cookie matches
3. Create Request Ticket
4. return Request Ticket
3. Receive login request – already logged in – invalid 
1. Check isAuthenticated – false
2. session id does not match
3. logout
4. return null
4. Receive isAuthenticated request – true
1. UST exists
2. session id matches
3. request ticket matches
4. UST has not expired
5. create new request ticket
6. return RT
5. Receive isAuthenticated request – UST does not exist
1. UST does not exist
2. return null
6. Receive isAuthenticated request – expired ticket
1. UST expired
2. call logout
3. return null
7. Recieve isAuthenticated request – RT does not match
1. Request ticket does not match
2. call logout
3. return null
8. Receive isAuthenticated request – session Id not match
1. Session id does not match
2. call logout
3. return null
9. Logout request – successful 
1. UST exists
2. delete UserId
3. delete Request Tickets
4. delete UST 
5. return true
10. Logout request – UST does not exist
1. nothing to do – return true 
4. Logout Service	
1. logout request – successful
1. call sso service logout
2. display logout page with login url

