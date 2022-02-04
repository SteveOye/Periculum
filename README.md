
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
        implementation 'com.github.AshaluwalaKazeem:Periculum:{latest version}'
    }
```


## API Reference

#### Customer's Data (VendorData Object)

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `phoneNumber` | `string` | **Required**. customer phone number |
| `bvn` | `string` | **Required**. customer bvn |
| `dti` | `Double` | **Required**. DTI |
| `loanTenure` | `Int` | **Required**. customer phone number |
| `token` | `string` | **Required**. API access token generated from periculum api |


``` kotlin
VendorData(
    phoneNumber = "+2348089182606", // customer phone number
    bvn = "0000000111", // customer bvn
    dti = 0.2, // dti
    loanTenure = 2, // loan tenure
    token = "" // token generated from periculum api
)
```

#### Periculum Parameters

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `vendorData`      | `VendorData Object` | **Required**. |
| `periculumCallback`      | `interface` | **Required**. Callback function to get request status |



``` kotlin
  Periculum.start(
    vendorData,
    object : PericulumCallback {
        override fun onSuccess(response: Response) {
            Log.i("TAG", response.responseBody!!)
        }

        override fun onError(message: String, errorType: ErrorType) {
            val errorMessage = message // Error message
            when(errorType) { // handle response error
                ErrorType.SmsPermissionError -> { }
                ...
            }
        }

    }
)
```


## How do I use Periculum?

Simple use cases will look something like this:

``` kotlin
import com.periculum.Periculum
import com.periculum.models.ErrorType
import com.periculum.models.PericulumCallback
import com.periculum.models.Response
import com.periculum.models.VendorData
...


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmsExtractTheme {
                Surface(color = MaterialTheme.colors.background) {

                    Button(
                        onClick = {
                            val vendorData = VendorData(
                                phoneNumber = "+2348089182606", // customer phone number
                                bvn = "0000000111", // customer bvn
                                dti = 0.2, // dti
                                loanTenure = 2, // loan tenure
                                token = "" // token generated from periculum api
                            )
                            Periculum.start(
                                vendorData,
                                object : PericulumCallback {
                                    override fun onSuccess(response: Response) {
                                        Log.i("TAG", response.responseBody!!)
                                    }

                                    override fun onError(message: String, errorType: ErrorType) {
                                        val errorMessage = message // Error message
                                        when(errorType) { // handle response error
                                            ErrorType.SmsPermissionError -> { }
                                            ErrorType.LocationPermissionError -> { }
                                            ErrorType.InternetConnectionError -> { }
                                            ErrorType.LocationNotEnabledError -> { }
                                            ErrorType.NetworkRequest -> { }
                                            ErrorType.InvalidVendorData -> { }
                                            ErrorType.UnknownError -> { }
                                        }
                                    }

                                }
                            )
                        },
                    ) {
                        Text(text = "Get Credit Score")
                    }


                }
            }
        }
    }

}
```
## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

