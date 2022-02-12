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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
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
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val text = rememberSaveable {
        mutableStateOf("waiting")
    }
    val state = rememberSaveable {
        mutableStateOf(false)
    }
   LazyColumn() {
       item {
           Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
               if (state.value) CircularProgressIndicator(modifier = Modifier.padding(20.dp))

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
               )

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
                   keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
               )
               Button(
                   onClick = {
                       try{
                           if(phoneNumberText.value.isEmpty() || bvnText.value.isEmpty() || dtiText.value.isEmpty() || loanTenureText.value.isEmpty()) {
                               Toast.makeText(context, "Please input all the parameters", Toast.LENGTH_LONG).show()
                           }else {
                               state.value = true
                               text.value = ""
                               Periculum.affordability(
                                   dti = 0.1,
                                   loanTenure = 32,
                                   statementKey = 62,
                                   token = "",
                                   object : PericulumCallback {
                                       override fun onSuccess(response: String) {
                                           Log.i(TAG, response)
                                           state.value = false
                                           text.value = "Sucess --->\t\t $response"
                                       }

                                       override fun onError(
                                           message: String,
                                           errorType: ErrorType
                                       ) {
                                           text.value = "$errorType ---> $message" // Error message
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
                           }
                       }catch (e: Exception) {
                           Toast.makeText(context, "Error occurred in the parameters", Toast.LENGTH_LONG).show()
                       }

                   },
                   modifier = Modifier.padding(20.dp)
               ) {
                   Text(text = "start process")
               }
               Text(text = text.value.replace("\\n", "\n"))
           }
       }
   }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SmsExtractTheme {
        Greeting()
    }
}