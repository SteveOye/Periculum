package tech.smallwonder.smsextract

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.periculum.Periculum
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
   LazyColumn() {
       item {
           Button(
               onClick = {
                   coroutineScope.launch {
                       text.value = Periculum.start(VendorData("skdsdfkj", "sdkfsdk", phoneNumber = "08089182606", bvn = "2k2kj2")).message
                   }
               }
           ) {
               Text(text = "start process")
           }
           Text(text = text.value.replace("\\n", "\n"))
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