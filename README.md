
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
	        implementation 'com.github.AshaluwalaKazeem:Periculum:v1.0.1-beta'
	}
```


## API Reference

#### Anaalytics Parameters


| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `phoneNumber` | `String` | **Required**. customer phone number |
| `bvn` | `String` | **Required**. customer bvn |
| `token` | `String` | **Required**. API access token generated from periculum api |
| `periculumCallback`      | `Interface` | **Required**. Callback function to get request status |


``` kotlin
Periculum.analytics(
    phoneNumber = "+2348089182606", // customer phone number
    bvn = "0000000111", // customer bvn
    token = "" // token generated from periculum api
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


#### Affordability Parameters


| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `dti` | `Double` | **Required**. DTI |
| `loanTenure` | `Int` | **Required**. Loan Tenure |
| `statementKey` | `Int` | **Required**. Statement Key |
| `token` | `String` | **Required**. API access token generated from periculum api |
| `periculumCallback`      | `interface` | **Required**. Callback function to get request status |



``` kotlin
Periculum.affordability(
    dti = 0.1, // DTI
    loanTenure = 32, // Loan Tenure
    statementKey = 932, // Pass a valid statement key
    token = "", // token generated from periculum api
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
                ErrorType.InternetConnectionError -> {
                    Log.e("TAG", "InternetConnectionError")
                }
                ...
            }
        }
    }
)
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


## How do I use Periculum?

Simple use cases will look something like this:

**For Analytics:**

``` kotlin
import com.periculum.Periculum
import com.periculum.models.ErrorType
import com.periculum.models.PericulumCallback
...


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmsExtractTheme {
                Surface(color = MaterialTheme.colors.background) {

                    Button(
                        onClick = {
                            Periculum.analytics(
                                phoneNumber = "+2348089182606", // customer phone number
                                bvn = "0000000111", // customer bvn
                                token = "", // token generated from periculum api
                                object : PericulumCallback {
                                    override fun onSuccess(response: String) {
                                        Log.i(TAG, response)
                                    }

                                    override fun onError(
                                        message: String,
                                        errorType: ErrorType
                                    ) {
                                        Log.i(TAG, "Error type ---> $errorType") // Error Type
                                        Log.i(TAG, "Error message ---> $message") // Error message

                                        when (errorType) { // handle response error
                                            ErrorType.SmsPermissionError -> {
                                                Log.e(TAG, "SmsPermissionError")
                                            }
                                            ErrorType.LocationPermissionError -> {
                                                Log.e(TAG, "LocationPermissionError")
                                            }
                                            ErrorType.InternetConnectionError -> {
                                                Log.e(TAG, "InternetConnectionError")
                                            }
                                            ErrorType.LocationNotEnabledError -> {
                                                Log.e(TAG, "LocationNotEnabledError")
                                            }
                                            ErrorType.NetworkRequest -> {
                                                Log.e(TAG, "NetworkRequest")
                                            }
                                            ErrorType.InvalidToken -> {
                                                Log.e(TAG, "InvalidToken")
                                            }
                                            ErrorType.InvalidData -> {
                                                Log.e(TAG, "InvalidData")
                                            }
                                            ErrorType.UnknownError -> {
                                                Log.e(TAG, "UnknownError")
                                            }
                                        }
                                    }
                                }
                            )
                        },
                    ) {
                        Text(text = "Get Analytics)
                    }


                }
            }
        }
    }
}
```



**For Affordability:**

``` kotlin
import com.periculum.Periculum
import com.periculum.models.ErrorType
import com.periculum.models.PericulumCallback
...


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmsExtractTheme {
                Surface(color = MaterialTheme.colors.background) {

                    Button(
                        onClick = {
                            Periculum.affordability(
                                dti = 0.1, // DTI
                                loanTenure = 32, // Loan Tenure
                                statementKey = 932, // Pass a valid statement key
                                token = "", // token generated from periculum api
                                object : PericulumCallback {
                                    override fun onSuccess(response: String) {
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
                                            ErrorType.InvalidData -> {
                                                Log.e(TAG, "InvalidData")
                                            }
                                            ErrorType.UnknownError -> {
                                                Log.e(TAG, "UnknownError")
                                            }
                                        }
                                    }
                                }
                            )
                        },
                    ) {
                        Text(text = "Get Affordability)
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

