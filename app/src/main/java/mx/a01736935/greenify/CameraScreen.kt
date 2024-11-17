package mx.a01736935.greenify

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material3.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

/*
class CameraScreen : AppCompatActivity(){
    private lateinit var scanQrBtn : Button
    private lateinit var scannedValueTv : TextView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        scanQrBtn = findViewById(R.id.scanQrBtn)
        scannedValueTv = findViewById(R.id.scannedValueTv)
        registerUiListener()
    }
    private fun registerUiListener(){
        scanQrBtn.setOnClickListener {
            scannerLauncher.launch(
                ScanOptions().setPrompt("Scan Qr Code")
                .setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            )
        }
    }
    private val scannerLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    ){result ->
        if (result.contents == null){
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        }else{
            scannedValueTv.text = buildString {
                append("Scanned Value : ")
                append(result.contents)
            }
        }
    }
}
*/

@Composable
fun CameraView(navController: NavController) {
    val context = LocalContext.current
    val scannedValue = remember { mutableStateOf("") }

    // Lanzador para ZXing
    val scannerLauncher = rememberLauncherForActivityResult(
        contract = ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
        } else {
            scannedValue.value = result.contents
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            scannerLauncher.launch(
                ScanOptions()
                    .setPrompt("Scan QR Code")
                    .setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            )
        }) {
            Text("Scan QR Code")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (scannedValue.value.isEmpty()) "Scanned value will appear here" else "Scanned Value: ${scannedValue.value}",
            color = Color.Black,
            fontSize = 16.sp
        )
    }
}
