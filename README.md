# Auth0-Passwordless
OTP based authentication via mail using Auth0 passwordless.

## Installation

1. Clone or Download the project source. 

2. Build and run the app, once done you should be able to see the following:

      <img src="https://github.com/abhishekspeer/Auth0-Passwordless/blob/master/img/1.png" alt="1" width="200"/>


3. Enter the email-ID using which you wish to authenticate

      <img src="https://github.com/abhishekspeer/Auth0-Passwordless/blob/master/img/2.png" alt="2" width="200"/>

4. An OTP will be sent to the email for confirmation.

      <img src="https://github.com/abhishekspeer/Auth0-Passwordless/blob/master/img/3.png" alt="3" width="200"/>

5. Enter the OTP receieved to authenticate.

      <img src="https://github.com/abhishekspeer/Auth0-Passwordless/blob/master/img/4.png" alt="4" width="200"/>


## API Demonstration

A few code snippets that can be used to call these API endpoints in different scenarios.

- To request an OTP, you can use the following cURL command:

#### Request
```
curl --request POST \
  --url 'https://dev-25x9oi7h.us.auth0.com/passwordless/start' \
  --header 'content-type: application/json' \
  --data '{"client_id": "rotYO6RMfRsIaL6cF2UMFDzF4fFeY03P",  "connection": "email",   "email": "abhishek.abhishekgaur.gaur@gmail.com",  "send": "code"}'
```
 
 Using test domain and client ID for the test application I've made in these cURL commands, DO NOT share these if in production. Needless to say, you need replace the "email" (currently added mine as an example) with the email you want to authenticate with. 

#### Response

```
{
    "_id": "610c62410858457944df09e8",
    "email": "abhishek.abhishekgaur.gaur@gmail.com",
    "email_verified": false
}
```
- Once called, you should recieve an OTP on your email which you need to authenticate the user. You can use the following to authenticate:

#### Request

```
curl --request POST \
  --url 'https://dev-25x9oi7h.us.auth0.com/oauth/token' \
  --header 'content-type: application/json' \
  --data '{"grant_type": "http://auth0.com/oauth/grant-type/passwordless/otp", "client_id": "rotYO6RMfRsIaL6cF2UMFDzF4fFeY03P", "username": "abhishek.abhishekgaur.gaur@gmail.com", "otp": "123456", "realm": "email", "audience": "https://dev-25x9oi7h.us.auth0.com/api/v2/", "scope": "openid profile email"}'
```
 
Again, here you need to replace the email with the one you've used earlier and add the OTP you've recieved against "otp". Once added, it'll authenticate the user when called.

#### Response

```
{
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlBrYUQ2T1lRU2h1WFd2MUhkZktXTSJ9.",
    "id_token": "9BMO90slfbXu8NFwROFE4RWUaFyUT964axBsKVETNY8TT_cCelc2dp1W5oGpWhaWwkAZH-ApCON66Jdkt7wlGfcG4Ie4S4rMnKGEy6A",
    "scope": "openid profile email read:current_user update:current_user_metadata delete:current_user_metadata create:current_user_metadata create:current_user_device_credentials delete:current_user_device_credentials update:current_user_identities",
    "expires_in": 86400,
    "token_type": "Bearer"
}
```

## Contributions
All kinds of contributions to improve the source are welcome! You can initiate a patch [here](https://github.com/abhishekspeer/Auth0-Passwordless/pulls).

## Resources
- The official Auth0 Passwordless [documentation](https://auth0.com/docs/libraries/auth0-android/auth0-android-passwordless)
- The Android toolkit for Auth0 API [source](https://github.com/auth0/Auth0.Android)

#

#### Made with :heart: and :coffee:
