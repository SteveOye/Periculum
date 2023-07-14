package tech.smallwonder.smsextract

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.periculum.Periculum
import com.periculum.internal.models.*
import com.periculum.models.*
import tech.smallwonder.smsextract.ui.theme.SmsExtractTheme

const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmsExtractTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainView()
                }
            }
        }
    }
}

@Composable
fun MainView() {

    val context = LocalContext.current
    val text = rememberSaveable {
        mutableStateOf("")
    }
    val state = rememberSaveable {
        mutableStateOf(false)
    }

    var tabIndex by remember { mutableStateOf(0) }
    val tabData = listOf(
        "Analytics",
        "Affordability",
    )

    var key: String = "Enter key"

    Column(Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabData.forEachIndexed { index, text ->
                Tab(selected = tabIndex == index, onClick = {
                    tabIndex = index
                }, text = {
                    Text(text = text)
                })
            }
        }

        LazyColumn(Modifier.padding(20.dp)) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (state.value) CircularProgressIndicator(modifier = Modifier.padding(20.dp))

                    if(tabIndex == 0) {
                        Button(
                            onClick = {

                                Periculum.analyticsV1(
                                    phoneNumber = "09012234567",
                                    bvn = "349966",
                                    publicKey = "nucleusis123" ,
                                    periculumCallback = object : PericulumCallback {

                                        override fun onSuccess(response: String) {
                                            text.value = "Success --->\t\t ${response}"
                                        }

                                        override fun onError(
                                            message: String,
                                            errorType: ErrorType
                                        ) {
                                            text.value = "Error type ---> $errorType" // Error Type
                                            text.value = "Error message ---> $message" // Error message
                                            Toast.makeText(context, message, Toast.LENGTH_LONG)
                                                .show()
                                            state.value = false

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
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(text = "Generate Mobile Analysis V1")
                        }
                        Text(text = text.value.replace("\\n", "\n"))

                        Button(
                            onClick = {

                                Periculum.analyticsV2(
                                    phoneNumber = "09012234567",
                                    bvn = "349966",
                                    publicKey = "periculumprod123" ,
                                    periculumCallback = object : MobileInsightCallback {

                                        override fun onSuccess(response: OverviewKey) {
                                            text.value = "Success --->\t\t ${response.mobileInsightsOverviewKey}"
                                        }

                                        override fun onError(
                                            message: String,
                                            errorType: ErrorType
                                        ) {
                                            text.value = "Error type ---> $errorType" // Error Type
                                            text.value = "Error message ---> $message" // Error message
                                            Toast.makeText(context, message, Toast.LENGTH_LONG)
                                                .show()
                                            state.value = false

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
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(text = "Generate Mobile Analysis V2")
                        }


                        Button(
                            onClick = {

                                Periculum.updateAnalyticsV2(
                                    phoneNumber = "09012234567",
                                    bvn = "349966",
                                    publicKey = "nucleusis123" ,
                                    overviewKey = "6",
                                    periculumCallback = object : MobileInsightCallback {

                                        override fun onSuccess(response: OverviewKey) {
                                            text.value = "Success --->\t\t ${response.mobileInsightsOverviewKey}"
                                        }

                                        override fun onError(
                                            message: String,
                                            errorType: ErrorType
                                        ) {
                                            text.value = "Error type ---> $errorType" // Error Type
                                            text.value = "Error message ---> $message" // Error message
                                            Toast.makeText(context, message, Toast.LENGTH_LONG)
                                                .show()
                                            state.value = false

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
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(text = "Update Mobile Analysis V2")
                        }
                        Text(text = text.value.replace("\\n", "\n"))

                    }else {
                        }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SmsExtractTheme {
        MainView()
    }
}
