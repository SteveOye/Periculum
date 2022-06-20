
# Periculum (Android)

Periculum library is a powerful & easy to use credit score library for Android.
The library gives you credit score information for your customer. No stress :)


## Badges

[![MIT License](https://img.shields.io/apm/l/atomic-design-ui.svg?)](https://github.com/tterb/atomic-design-ui/blob/master/LICENSEs)

[![](https://img.shields.io/badge/jitpack-2.0.0-green)](https://jitpack.io/#AshaluwalaKazeem/Periculum)


## Compatibility

- Minimum Android SDK: Periculum requires a minimum API level of 23.
- Compile Android SDK: Periculum requires you to compile against API 31 or later.

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

