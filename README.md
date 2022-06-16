
# Periculum (Android)

Periculum library is a powerful & easy to use credit score library for Android.
The library gives you credit score information for your customer. No stress :)


## Badges

[![MIT License](https://img.shields.io/apm/l/atomic-design-ui.svg?)](https://github.com/tterb/atomic-design-ui/blob/master/LICENSEs)

[![](https://jitpack.io/v/AshaluwalaKazeem/Periculum.svg)](https://jitpack.io/#AshaluwalaKazeem/Periculum)


## Compatibility

- Minimum Android SDK: Periculum requires a minimum API level of 23.
- Compile Android SDK: Periculum requires you to compile against API 31 or later.
## Info

For this plugin to work, you must have a Periculum account and you'll also
need to use your merchant Id and secret key to generate a token from the
Periculum API.


## Installation

To get a Periculum library into your build:

**Step 1**. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories

``` gradle
allprojects {
    repositories {
        ...
	maven { url 'https://jitpack.io' }
    }
}
```

**Step 2**. Add the dependency

``` gradle
dependencies {
    implementation 'com.github.Periculum-io:Periculum:1.0.6-beta'
}
```


## Authentication

Your app must first identify and authorize itself against an authorization server by getting an access token before using any of Insights endpoints. For security concerns, make sure that the entire process of obtaining a token takes place on a remote server. So, once you've gotten the token from your server, you can utilize it to get analytics or affordability information by giving it into the token parameter.

To authenticate against the authorization server, make sure you have Periculum's client id and client secret. Your app will not be able to receive an access token and call endpoints on the Insights API without these. If you have not received your client id or client secret, please contact Periculum's usual support channel (email support@periculum.io).

Visit https://www.periculum.io/documentation/insights/#authenticationrequest for further information.

![Authorization process](https://github.com/AshaluwalaKazeem/Periculum/blob/master/auth.png)

1. Customer phone app authenticates with customer backend server.
2. Customer backend server should return a valid access token. 
...2a. If the customer backend server has not obtained an access token, then it should make a call with the customer's client credentials to the authorization server and obtain an access token. 
...2ai. The customer backend server should cache the access token. 
...2b. If the customer backend server has an existing cached access token that has not expired, then it should return the cached token. 
...2bi. If the customer access token is near expiry (5-15 minutes before expiry), then it should obtain a new access token with its client credentials.
3. The access token is either retrieved from the authorization server or the cache.
4. The customer backend server returns the access token to the customer's mobile app.
5. The customer's mobile app can now make an SDK call to submit mobile phone data using the access token.
6. The SDK method will call the Insights API with the access token.
   7 & 8. Insights API will validate the token.
9. Insights API will return the response to the SDK method.
10. The SDK method will return the response back to the customer mobile app.

## API Reference

#### Permission Required

```xml
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_SMS"/>
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
```


#### Error Types

These are the error types that can be used in the onError callback
to handle error cases.

| Parameter | Error Description                       |
| :-------- | :-------------------------------- |
| `InternetConnectionError`      | There is no access to the internet.  |
| `SmsPermissionError`      | Permission to read SMS messages from the device has been denied. |
| `LocationPermissionError`      | Permission to read the location of the device has been denied. |
| `LocationNotEnabledError`      | Location not enabled. |
| `UnknownError`      | Error Occurred. |
| `NetworkRequest`      | While submitting the request, a network error occurred. |
| `InvalidToken`      | Invalid access token |
| `InvalidData`      | An invalid parameter has been passed. |


``` kotlin
enum class ErrorType {
    InternetConnectionError,
    SmsPermissionError,
    LocationPermissionError,
    LocationNotEnabledError,
    UnknownError,
    NetworkRequest,
    InvalidToken,
    InvalidData
}
```

#### Callback Types

These are the callback for every request made

| action | method  | Callback                      | Description             |
| :-------- | :-------- | :-------------------------------- | :--------------------|
| Get Mobile Analysis     | `analyticsV1`  |  `PericulumCallback`     |  Returns an Array of MobileAnalysis Object  |
| Get Mobile Insight V2     | `analyticsV2`  |  `MobileInsightCallback`   |  Returns an OverviewKey object  that contains analysis key|


## How do I use Periculum?

See
[source code](https://github.com/AshaluwalaKazeem/Periculum/blob/master/app/src/main/java/tech/smallwonder/smsextract/MainActivity.kt) for full implementation.

Simple use cases will look something like this:

#### Anaalytics Parameters

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `phoneNumber` | `String` | **Optional**. customer phone number |
| `bvn` | `String` | **Optional**. customer bvn |
| `publicKey` | `String` | **Required**. Public Key attached to client account |
| `periculumCallback`      | `PericulumCallback` | **Required**. Callback function returns response |


``` kotlin
Periculum.analyticsV1(
    phoneNumber = "+2348089182606", // customer phone number 
    bvn = "0000000111", // customer bvn 
    publicKey = "" // publicKey attached client account 
    object : PericulumCallback {
        override fun onSuccess(response: String) {
            Log.i("TAG", response)
        }

        override fun onError(
            message: String,
            errorType: ErrorType
        ) {
            Log.e("TAG", "Error message ---> $message")
            when (errorType) { // handle response error
                ErrorType.SmsPermissionError -> {
                    Log.e("TAG", "SmsPermissionError")
                }
                ...
            }
        }
    }
)
```

``` kotlin
Periculum.analyticsV2(
    phoneNumber = "+2348089182606", // customer phone number (Optional)
    bvn = "0000000111", // customer bvn (Optional)
    publicKey = "" // publicKey attached client account 
    object : MobileInsightCallback {
      override fun onSuccess(response: OverviewKey) {
      text.value = "Success --->\t\t ${response.mobileInsightsOverviewKey}"
    }

        override fun onError(
            message: String,
            errorType: ErrorType
        ) {
            Log.e("TAG", "Error message ---> $message")
            when (errorType) { // handle response error
                ErrorType.SmsPermissionError -> {
                    Log.e("TAG", "SmsPermissionError")
                }
                ...
            }
        }
    }
)
```

**Update existing mobile analysis**

###### 

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `phoneNumber` | `String` | customer phone number |
| `bvn` | `String` | . customer bvn |
| `publicKey` | `String` | **Required**. Public Key attached to client account |
| `overviewKey` | `String` | **Required**. |
| `periculumCallback`      | `PericulumCallback` | **Required**. Callback function returns response |

```kotlin

Periculum.updateAnalyticsV2(
   phoneNumber = "",
   bvn = "",
   publicKey = "",
   overviewKey = "", // overview key of the user
    periculumCallback = object : MobileInsightCallback {
       override fun onSuccess(response: OverviewKey) {
          text.value = "Success --->\t\t ${response.mobileInsightsOverviewKey}"
       }

        override fun onError(
        	message: String,
                errorType: ErrorType
        ) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            when (errorType) { // handle response error
                ErrorType.InternetConnectionError -> {
                    	Log.e(TAG, "InternetConnectionError")
                }
                ErrorType.NetworkRequest -> {
                	Log.e(TAG, "NetworkRequest")
                }
                ErrorType.InvalidToken -> {
                	Log.e(TAG, "InvalidToken")
                }
		        ...
            }
    	}
    }
)
```


```
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmsExtractTheme {
                Surface(color = MaterialTheme.colors.background) {

                    Button(
                        onClick = {
                            Periculum.generateCreditScore(
                                statementKey = "932", // Pass a valid statement key
                                accessToken = "", // token generated from periculum api
                                object : GenerateCreditScoreCallback { //Callback response returns a CreditScore Model
                                    override fun onSuccess(response: CreditScore) {
                                        Log.i(TAG, response)
                                    }

                                    override fun onError(
                                        message: String,
                                        errorType: ErrorType
                                    ) {
                                        Log.i(TAG, "Error type ---> $errorType") // Error Type
                                        Log.i(TAG, "Error message ---> $message") // Error message

                                        when (errorType) { // handle response error
                                            ErrorType.InternetConnectionError -> {
                                                Log.e(TAG, "InternetConnectionError")
                                            }
                                            ErrorType.NetworkRequest -> {
                                                Log.e(TAG, "NetworkRequest")
                                            }
                                            ErrorType.InvalidToken -> {
                                                Log.e(TAG, "InvalidToken")
                                            }
                                            ...
                                        }
                                    }
                                }
                            )
                        },
                    ) {
                        Text(text = "Generate Credit Score")
                    }
                }
            }
        }
    }
}

```

## Required Permission

The permissions that must be granted in order for this library to function are listed below.
``` xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.READ_SMS"/>
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
```
## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

