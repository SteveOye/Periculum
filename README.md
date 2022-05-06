
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

## API Reference

#### Anaalytics Parameters


| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `phoneNumber` | `String` | **Optional**. customer phone number |
| `bvn` | `String` | **Optional**. customer bvn |
| `token` | `String` | **Required**. API access token generated from periculum api |
| `periculumCallback`      | `Interface` | **Required**. Callback function to get request status |


``` kotlin
Periculum.analytics(
    phoneNumber = "+2348089182606", // customer phone number (Optional)
    bvn = "0000000111", // customer bvn (Optional)
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
| `dti` | `Double` | **Required**. Debt to income ratio for the affordability analysis. |
| `loanTenure` | `Int` | **Required**. The period of the loan in months. |
| `statementKey` | `Int` | **Required**. The key of the statement to generate the affordability analysis for |
| `averageMonthlyTotalExpenses` | `Double` | **Optional**. Average Monthly Total Expenses |
| `averageMonthlyLoanRepaymentAmount` | `Double` | **Optional**. Average Monthly Loan Repayment Amount	 |
| `token` | `String` | **Required**. API access token generated from periculum api |
| `periculumCallback`      | `interface` | **Required**. Callback function to get request status |



``` kotlin
Periculum.affordability(
    dti = 0.1, // DTI
    loanTenure = 32, // Loan Tenure
    statementKey = 932, // Pass a valid statement key
    averageMonthlyTotalExpenses = 0.0, // Average Monthly Total Expenses (Optional)
    averageMonthlyLoanRepaymentAmount = 0.0, // Average Monthly Loan Repayment Amount (Optional)
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

#### Callback Types

These are the callback for every request made

| action | method  | Callback                      | Description             |
| :-------- | :-------- | :-------------------------------- | :--------------------|
| Generate Credit Score    | `generateCreditScore`  |  `GenerateCreditScoreCallback`     |  Returns a Credit Score Object  |
| Get Existing Credit Score      | `getCreditScore`  |  `GetCreditScoreCallback`   |  Return an Array of CreditScore object |
| Get Statement Transaction   | `getStatementTransaction`  | `GetStatementTransactionCallback`  |  Returns an Array of StatementTransaction object |
| Get Existing Statement Analytics  | `getStatment`  |  `GetStatementCallback`  |   Returns a Statement object  |
| Get Existing Statement Affordability Analysis  | `getAffordability`  | `GetAffordabilityCallback` | Returns an Array of Affordability object |
| Attach Customer Identification Information To A Statement  | `patchClientIdentification`  | `PatchIdentificationCallback`      | Returns a statusCode 200 on success|


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

See 
[source code](https://github.com/AshaluwalaKazeem/Periculum/blob/master/app/src/main/java/tech/smallwonder/smsextract/MainActivity.kt) for full implementation.

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
                                phoneNumber = "+2348089182606", // customer phone number (Optional)
                                bvn = "0000000111", // customer bvn (Optional)
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
                        Text(text = "Get Analytics")
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
                                averageMonthlyTotalExpenses = 0.0, // Average Monthly Total Expenses (Optional)
                                averageMonthlyLoanRepaymentAmount = 0.0, // Average Monthly Loan Repayment Amount (Optional)
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
                        Text(text = "Get Affordability")
                    }


                }
            }
        }
    }
}
```

**Generate Credit Score**

```kotlin

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

**Get Existing Credit Score**

```kotlin
Periculum.getCreditScore(
	statementKey = "125", //Statement Key
    accessToken = key , // Token
    periculumCallback = object : GetCreditScoreCallback {
        override fun onSuccess(response: Array<CreditScore>) {
            Log.i(TAG, response[0].baseScore.toString())
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
                }...
            }
		}
	}
)
```


**Get Statement Transaction**

```kotlin
Periculum.getCreditScore(
	statementKey = "125", //Statement Key
        accessToken = key , // Token
        periculumCallback = object : GetStatementTransactionCallback {
        	override fun onSuccess(response: Array<StatementTransaction>) {
                	Log.i(TAG, response[0].description.toString()
            }
            override fun onError(
            	message: String,
                    errorType: ErrorType
            ) {
            	text.value = "Error type ---> $errorType" // Error Type
                    text.value = "Error message ---> $message" // Error message
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
	
)
```

**Get Existing Statement Analytics**

```kotlin
Periculum.getStatement(
	statementKey = "125", //Statement Key
    accessToken = key , // Token
    periculumCallback = object : GetStatementCallback {
    	override fun onSuccess(response: Statements) {
            	Log.i(TAG, response[0].description.toString()
        }
        
        override fun onError(
        	message: String,
                errorType: ErrorType
        ) {
            text.value = "Error type ---> $errorType" // Error Type
            text.value = "Error message ---> $message" // Error message
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
                } ...
            }
        }
	}
)
```

**Get Existing Statement Affordability Analysis**

```kotlin

Periculum.getAfordability(
	statementKey = "125", //Statement Key
    accessToken = key , // Token
    periculumCallback = object : GetAffordabilityCallback {
    	override fun onSuccess(response: Array<Affordability>) {
            	Log.i(TAG, response[0].createdDate.toString()
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
)
```


#### Customer Identification Parameters

###### ClientData
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `statementKey` | `String` | **required**. |
| `identification` | `List<ClientIdentification>` | **required**. A list of different identification means |

###### ClientIdentification
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `identifierName` | `String` | **required** Identification type name e.g: bvn or nin. |
| `identifierValue` | `String` | **required**. Identification value/code.|


**Attach Customer Identification Information To A Statement**

```kotlin

//Pass in a ClientData containing the key and a list of ClientIdentification

val identificationData = 
	ClientIdentification(
		identifierName = "bvn",
		identifierValue ="2345"
	)
val listOfItems = 
mutableListOf<ClientIdentification>(identificationData)

val clientData = ClientData(
	statementKey = 125,
	identificationData = listOfItems
)

Periculum.patchClientIdentification(
    accessToken = key , // Token
	clientData = clientData, //body ClientData
    periculumCallback = object : PatchIdentificationCallback {
    	override fun onSuccess(response: String) {
            Log.i(TAG, response)
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
                }...
            }	
	   }
	   
    }
)
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

