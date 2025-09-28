package app.linksheet.lib.flavors

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import fe.composekit.intent.buildIntent
import fe.std.result.IResult
import fe.std.result.getOrNull
import fe.std.result.isSuccess
import fe.std.result.tryCatch
import kotlin.collections.iterator

public object LinkSheet : LinkSheetApp(
    basePackage = "fe.linksheet",
    flavors = setOf(Flavor.Default, Flavor("pro", 1)),
) {
    private val bottomSheetCls: String = "${basePackage}.activity.BottomSheetActivity"

    public fun find(context: Context): List<ActivityInfo> {
        return sortedApps.mapNotNull {
            context.packageManager.getActivityInfoOrNull(ComponentName(it.packageName, bottomSheetCls))
        }
    }

    private fun PackageManager.getActivityInfoOrNull(componentName: ComponentName): ActivityInfo? {
        return tryCatch { getActivityInfo(componentName, 0) }.getOrNull()
    }

    public fun start(activity: Activity, uri: Uri): ComponentName? {
        for ((packageName, info) in packageNames) {
            val cmp = ComponentName(packageName, bottomSheetCls)
            val result = tryStart(activity, uri, cmp)
            if (result.isSuccess()) return cmp
        }

        return null
    }

    public fun createIntent(uri: Uri, componentName: ComponentName): Intent {
        return buildIntent(Intent.ACTION_VIEW, uri, componentName) {
            addCategory(Intent.CATEGORY_BROWSABLE)
        }
    }

    private fun tryStart(activity: Activity, uri: Uri, cmp: ComponentName): IResult<Unit> {
        val intent = createIntent(uri, cmp)
        return tryCatch { activity.startActivity(intent) }
    }
}
