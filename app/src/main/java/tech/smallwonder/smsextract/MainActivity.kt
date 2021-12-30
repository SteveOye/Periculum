package tech.smallwonder.smsextract

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.periculum.Periculum
import com.periculum.models.ErrorType
import com.periculum.models.PericulumCallback
import com.periculum.models.Response
import com.periculum.models.VendorData
import kotlinx.coroutines.launch
import tech.smallwonder.smsextract.ui.theme.SmsExtractTheme

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
               if(state.value) CircularProgressIndicator(modifier = Modifier.padding(20.dp))
               Button(
                   onClick = {
                           state.value = true
                           Periculum.start(
                               VendorData("skdsdfkj", "sdkfsdk", phoneNumber = "08089182606", bvn = "2k2kj2"),
                               object : PericulumCallback {
                                   override fun onSuccess(response: Response) {
                                       text.value = response.message
                                       state.value = false
                                   }

                                   override fun onError(message: String, errorType: ErrorType) {
                                       text.value = message
                                       Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                       state.value = false
                                   }

                               }
                           )

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