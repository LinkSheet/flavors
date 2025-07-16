package app.linksheet

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import fe.composekit.intent.buildIntent
import fe.linksheet.lib.flavors.Flavor
import fe.linksheet.lib.flavors.LinkSheetApp
import fe.std.result.IResult
import fe.std.result.getOrNull
import fe.std.result.isSuccess
import fe.std.result.tryCatch
import kotlin.collections.component1
import kotlin.collections.component2
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

    private fun tryStart(activity: Activity, uri: Uri, cmp: ComponentName): IResult<Unit> {
        val intent = buildIntent(Intent.ACTION_VIEW, uri, cmp) {
            addCategory(Intent.CATEGORY_BROWSABLE)
        }
        return tryCatch { activity.startActivity(intent) }
    }
}
