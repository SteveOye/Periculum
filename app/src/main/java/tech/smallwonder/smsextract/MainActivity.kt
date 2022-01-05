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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.periculum.Periculum
import com.periculum.models.ErrorType
import com.periculum.models.PericulumCallback
import com.periculum.models.Response
import com.periculum.models.VendorData
import tech.smallwonder.smsextract.ui.theme.SmsExtractTheme
import java.lang.Exception

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

               val merchantIdText = rememberSaveable {
                   mutableStateOf("")
               }
               OutlinedTextField(
                   value = merchantIdText.value,
                   onValueChange = {
                       merchantIdText.value = it
                   },
                   placeholder = {
                       Text(text = "Please enter merchantId")
                   },
                   label = {
                       Text(text = "Merchant Id")
                   },
                   keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Sentences),
                   modifier = Modifier.padding(top = 20.dp)
               )

               val secretKeyText = rememberSaveable {
                   mutableStateOf("")
               }
               OutlinedTextField(
                   value = secretKeyText.value,
                   onValueChange = {
                       secretKeyText.value = it
                   },
                   placeholder = {
                       Text(text = "Please enter secret key")
                   },
                   label = {
                       Text(text = "Secret key")
                   },
                   keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Sentences),
               )

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
               val errorState = rememberSaveable {
                   mutableStateOf(ErrorType.Null)
               }
               val color = if(errorState.value == ErrorType.NetworkRequest){
                   Color.Red
               }else if(errorState.value == ErrorType.UnknownError) {
                   Color.Blue
               }else {
                   Color.Black
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
                           if(merchantIdText.value.isEmpty() || secretKeyText.value.isEmpty() || phoneNumberText.value.isEmpty() || bvnText.value.isEmpty() || dtiText.value.isEmpty() || loanTenureText.value.isEmpty()) {
                               Toast.makeText(context, "Please input all the parameters", Toast.LENGTH_LONG).show()
                           }else {
                               errorState.value = ErrorType.Null
                               state.value = true
                               Periculum.start(
                                   VendorData(
                                       merchantId = secretKeyText.value,
                                       secretKey = secretKeyText.value,
                                       phoneNumber = phoneNumberText.value,
                                       bvn = bvnText.value,
                                       dti = dtiText.value.toDouble(),
                                       loanTenure = loanTenureText.value.toInt()
                                   ),
                                   object : PericulumCallback {
                                       override fun onSuccess(response: Response) {
                                           text.value = response.message
                                           state.value = false
                                           errorState.value = ErrorType.UnknownError
                                       }

                                       override fun onError(message: String, errorType: ErrorType) {
                                           text.value = message
                                           Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                           state.value = false
                                           errorState.value = ErrorType.NetworkRequest
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
               Text(text = text.value.replace("\\n", "\n"), color = color)
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