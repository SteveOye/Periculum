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
import com.periculum.models.ErrorType
import com.periculum.models.PericulumCallback
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
                        val phoneNumberText = rememberSaveable {
                            mutableStateOf("")
                        }
                        OutlinedTextField(
                            value = phoneNumberText.value,
                            onValueChange = {
                                phoneNumberText.value = it
                            },
                            placeholder = {
                                Text(text = "Please enter phone number")
                            },
                            label = {
                                Text(text = "Phone Number")
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            maxLines = 6,
                            modifier = Modifier.fillMaxWidth()
                        )

                        val bvnText = rememberSaveable {
                            mutableStateOf("")
                        }
                        OutlinedTextField(
                            value = bvnText.value,
                            onValueChange = {
                                bvnText.value = it
                            },
                            placeholder = {
                                Text(text = "Please enter bvn")
                            },
                            label = {
                                Text(text = "BVN")
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            maxLines = 6,
                            modifier = Modifier.fillMaxWidth()
                        )

                        val tokenText = rememberSaveable {
                            mutableStateOf("")
                        }
                        OutlinedTextField(
                            value = tokenText.value,
                            onValueChange = {
                                tokenText.value = it
                            },
                            placeholder = {
                                Text(text = "Please enter token")
                            },
                            label = {
                                Text(text = "Token")
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            maxLines = 6,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(
                            onClick = {
                                state.value = true
                                text.value = ""
                                Periculum.analytics(
                                    phoneNumber = phoneNumberText.value,
                                    bvn = bvnText.value,
                                    token = tokenText.value,
                                    object : PericulumCallback {
                                        override fun onSuccess(response: String) {
                                            Log.i(TAG, response)
                                            state.value = false
                                            text.value = "Success --->\t\t$response"
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
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(text = "start process")
                        }
                        Text(text = text.value.replace("\\n", "\n"))
                    }else {

                        val dtiText = rememberSaveable {
                            mutableStateOf("")
                        }
                        OutlinedTextField(
                            value = dtiText.value,
                            onValueChange = {
                                dtiText.value = it
                            },
                            placeholder = {
                                Text(text = "Please enter dti")
                            },
                            label = {
                                Text(text = "DTI")
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            maxLines = 6,
                            modifier = Modifier.fillMaxWidth()
                        )

                        val loanTenureText = rememberSaveable {
                            mutableStateOf("")
                        }
                        OutlinedTextField(
                            value = loanTenureText.value,
                            onValueChange = {
                                loanTenureText.value = it
                            },
                            placeholder = {
                                Text(text = "Please enter Loan tenure")
                            },
                            label = {
                                Text(text = "Loan Tenure")
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            maxLines = 6,
                            modifier = Modifier.fillMaxWidth()
                        )

                        val statementKeyText = rememberSaveable {
                            mutableStateOf("")
                        }
                        OutlinedTextField(
                            value = statementKeyText.value,
                            onValueChange = {
                                statementKeyText.value = it
                            },
                            placeholder = {
                                Text(text = "Please enter statement key")
                            },
                            label = {
                                Text(text = "Statement Key")
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            maxLines = 6,
                            modifier = Modifier.fillMaxWidth()
                        )

                        val tokenText = rememberSaveable {
                            mutableStateOf("")
                        }
                        OutlinedTextField(
                            value = tokenText.value,
                            onValueChange = {
                                tokenText.value = it
                            },
                            placeholder = {
                                Text(text = "Please enter token")
                            },
                            label = {
                                Text(text = "Token")
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            maxLines = 6,
                            modifier = Modifier.fillMaxWidth()
                        )

                        val averageMonthlyTotalExpensesText = rememberSaveable {
                            mutableStateOf("")
                        }
                        OutlinedTextField(
                            value = averageMonthlyTotalExpensesText.value,
                            onValueChange = {
                                averageMonthlyTotalExpensesText.value = it
                            },
                            placeholder = {
                                Text(text = "Please Enter Average Monthly Total Expenses")
                            },
                            label = {
                                Text(text = "Average Monthly Total Expenses")
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            maxLines = 6,
                            modifier = Modifier.fillMaxWidth()
                        )

                        val averageMonthlyLoanRepaymentAmountText = rememberSaveable {
                            mutableStateOf("")
                        }
                        OutlinedTextField(
                            value = averageMonthlyLoanRepaymentAmountText.value,
                            onValueChange = {
                                averageMonthlyLoanRepaymentAmountText.value = it
                            },
                            placeholder = {
                                Text(text = "Please Enter Average Monthly Loan Repayment Amount")
                            },
                            label = {
                                Text(text = "Average Monthly Loan Repayment Amount")
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            maxLines = 6,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(
                            onClick = {
                                state.value = true
                                text.value = ""
                                if(dtiText.value.isEmpty() || loanTenureText.value.isEmpty() || statementKeyText.value.isEmpty()){
                                    Toast.makeText(context, "Please make sure you input value for DTI, loanTenure and statement key", Toast.LENGTH_LONG).show()
                                    state.value = false
                                }else {
                                    Periculum.affordability(
                                        dti = dtiText.value.toDouble(),
                                        loanTenure = loanTenureText.value.toInt(),
                                        statementKey = statementKeyText.value.toInt(),
                                        token = tokenText.value,
                                        averageMonthlyTotalExpenses = if(averageMonthlyTotalExpensesText.value.trim().isNotEmpty()) averageMonthlyTotalExpensesText.value.toDouble() else null,
                                        averageMonthlyLoanRepaymentAmount = if(averageMonthlyLoanRepaymentAmountText.value.trim().isNotEmpty()) averageMonthlyLoanRepaymentAmountText.value.toDouble() else null,
                                        periculumCallback = object : PericulumCallback {
                                            override fun onSuccess(response: String) {
                                                Log.i(TAG, response)
                                                state.value = false
                                                text.value = "Success --->\t\t $response"
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
                                }
                            },
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(text = "start process")
                        }
                        Button(
                            onClick = {
                                var key = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik1VSkJOVUk0UkRFek9FVTBORGd4UWpVMVJqTTJPVEJEUXpRMFF6bEJRa1F6UWpnd1JETkVSQSJ9.eyJodHRwczovL2luc2lnaHRzLXBlcmljdWx1bS5jb20vdGVuYW50IjoibnVjbGV1c2lzIiwiaXNzIjoiaHR0cHM6Ly9wZXJpY3VsdW0tdGVjaG5vbG9naWVzLWluYy5hdXRoMC5jb20vIiwic3ViIjoiSDR1VHJzdjJoMGlEVGlTMDR2NmVGWmNpdTNLMGJvWnJAY2xpZW50cyIsImF1ZCI6Imh0dHBzOi8vYXBpLmluc2lnaHRzLXBlcmljdWx1bS5jb20iLCJpYXQiOjE2NTA5MTQ2NzksImV4cCI6MTY1MTUxOTQ3OSwiYXpwIjoiSDR1VHJzdjJoMGlEVGlTMDR2NmVGWmNpdTNLMGJvWnIiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.X6i4rNvo21ycR4l8Vbdovkyhc7lBYEdQ3zOzexOLO00XfcTO08wihZXuCHESCuYWxej39FLgNvoYINIqenRTEJkkyWR08KC_ONDUXpYvmJivFfmMzaBcDv4J9UvPxb0den-LMT-dfbAiwqQGXL1DadAo3nMHuzUFVpeLcJZ4lqQplulLPKuq9Mbsjfe4XC3Y6pm0sBd-0KI_MMYEBKHIw9U_arR_wf7GmcD3R_DZ-kzOwvmknu4VkmhGlAgzrZqq2uHUXzHBUOY99i_P2PktB5Ty7d9yKksSi5fJgQn6yTbRKghu5keZQ5lwaTfuq5PGovSIF-jDwiFHhf0qT3URsw";

                                Periculum.getCreditScore(
                                    statementKey = "127",
                                    token = key ,
                                    periculumCallback = object : PericulumCallback {
                                        override fun onSuccess(response: String) {
                                            Log.i(TAG, response)
                                            state.value = false
                                            text.value = "Success --->\t\t $response"
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
                            Text(text = "start score")
                        }
                        Text(text = text.value.replace("\\n", "\n"))
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