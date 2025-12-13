package app.linksheet.lib.interconnect

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import app.linksheet.lib.flavors.LinkSheetComponent
import fe.linksheet.interconnect.ILinkSheetService
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

public class LinkSheetConnector(
    public val interconnect: LinkSheetComponent.Interconnect,
) {
    private val permission = "${interconnect.packageName}.permission.INTERCONNECT"

    public fun checkPermission(context: Context, launcher: ActivityResultLauncher<String>): Boolean {
        if (!hasPermission(context)) {
            launcher.launch(permission)
            return false
        }

        return true
    }

    public fun hasPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    public fun bindService(context: Context, onBound: (LinkSheetServiceConnection) -> Unit): BindResult {
        if (!hasPermission(context)) return BindResult.NoPermission

        val intent = Intent(Intent.ACTION_VIEW).apply {
            `package` = interconnect.packageName
            component = interconnect.componentName
        }
        val connection = object : LinkSheetServiceConnection() {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                this.service = ILinkSheetService.Stub.asInterface(service)
                onBound(this)
            }

            override fun disconnect() {
                context.unbindService(this)
            }
        }

        return runCatching {
            ContextCompat.startForegroundService(context, intent)
            var flags = Context.BIND_AUTO_CREATE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                flags = flags or Context.BIND_ALLOW_ACTIVITY_STARTS
            }

            context.bindService(intent, connection, flags)
        }.map { if (it) BindResult.Success else BindResult.Failure }.getOrElse { BindResult.Error(it) }
    }


    /**
     * A convenience function for binding the Interconnect service.
     */
    public suspend fun bindService(context: Context): LinkSheetServiceConnection {
        return suspendCancellableCoroutine { continuation ->
            bindService(context) { continuation.resume(it) }
        }
    }
}

public sealed interface BindResult {
    public data object NoPermission : BindResult
    public data object Success : BindResult
    public data object Failure : BindResult
    public class Error(public val error: Throwable) : BindResult
}
