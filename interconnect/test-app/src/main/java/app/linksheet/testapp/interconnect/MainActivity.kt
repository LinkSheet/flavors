package app.linksheet.testapp.interconnect

import android.content.ComponentName
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import app.linksheet.lib.flavors.LinkSheet
import app.linksheet.lib.interconnect.LinkSheetConnector
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val linksheet = LinkSheet(basePackage = "fe.linksheet.debug")
    val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        if (result) {
            lifecycleScope.launch { bind() }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val scope = rememberCoroutineScope()
            Box(modifier = Modifier.safeContentPadding()) {
                Button(onClick = {
                    scope.launch { bind() }
                }) {
                    Text(text = "Bind service")
                }
            }
        }
    }

    private suspend fun bind() {
        val component = linksheet.findInterconnect(this@MainActivity).firstOrNull()
        Log.d("MainActivity", "$component")
        if (component == null) return

        val connector = LinkSheetConnector(component)
        val hasPermission = connector.checkPermission(this@MainActivity, requestPermissionLauncher)
        Log.d("MainActivity", "hasPermission=$hasPermission")
        if (hasPermission) {
            val connection = connector.bindService(this@MainActivity)
            val cmp = ComponentName(this@MainActivity, MainActivity::class.java)
            val result = connection.selectDomainsWithResult(cmp.packageName, listOf("linksheet.app"), cmp)
        }
    }
}
