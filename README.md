# Single Signon 

I am attempting to write a light weight single signon solution. This is intended for learning only.
Read TedSingleSignon.odt open office document for artitechual design on how I want to approach this.

3 Major Components:
  * Client jar will contain a filter to call sso server
  * sso server is to manage service/request tickets.
  * Login deployable to manage login and user attributes to be passed back to sso server.

