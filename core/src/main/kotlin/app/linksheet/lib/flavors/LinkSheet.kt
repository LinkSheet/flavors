package app.linksheet.lib.flavors

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.net.Uri

public class LinkSheet(
    basePackage: String = "fe.linksheet",
    flavors: Set<Flavor> = setOf(Flavor.Default, Flavor("pro", 1)),
) : LinkSheetApp(basePackage = basePackage, flavors = flavors) {
    private val bottomSheetCls: String = "fe.linksheet.activity.BottomSheetActivity"
    private val interconnectCls: String = "app.linksheet.service.InterconnectService2"

    public fun findBottomSheet(context: Context): List<LinkSheetComponent.BottomSheet> {
        return sortedApps.mapNotNull {
            val cmp = ComponentName(it.packageName, bottomSheetCls)
            val activity = context.packageManager.getActivityInfoOrNull(cmp) ?: return@mapNotNull null

            LinkSheetComponent.BottomSheet(cmp, activity)
        }
    }

    public fun findInterconnect(context: Context): List<LinkSheetComponent.Interconnect> {
        return sortedApps.mapNotNull { app ->
            val cmp = ComponentName(app.packageName, interconnectCls)
            val service = context.packageManager.getServiceInfoOrNull(cmp) ?: return@mapNotNull null

            LinkSheetComponent.Interconnect(cmp, service)
        }
    }

    private fun PackageManager.getActivityInfoOrNull(componentName: ComponentName): ActivityInfo? {
        return runCatching { getActivityInfo(componentName, 0) }.getOrNull()
    }

    private fun PackageManager.getServiceInfoOrNull(componentName: ComponentName): ServiceInfo? {
        return runCatching { getServiceInfo(componentName, 0) }.getOrNull()
    }
}

public object BottomSheet {
    public fun start(
        components: List<LinkSheetComponent.BottomSheet>,
        activity: Activity,
        uri: Uri
    ): LinkSheetComponent.BottomSheet? {
        for (component in components) {
            val success = start(component, activity, uri)
            if (success) return component
        }

        return null
    }

    public fun start(component: LinkSheetComponent.BottomSheet, activity: Activity, uri: Uri): Boolean {
        val result = tryStart(activity, uri, component.componentName)
        return result.isSuccess
    }

    private fun tryStart(activity: Activity, uri: Uri, cmp: ComponentName): Result<Unit> {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.component = cmp
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        return runCatching { activity.startActivity(intent) }
    }
}

public sealed class LinkSheetComponent(public val componentName: ComponentName) {
    public val packageName: String = componentName.packageName


    public class BottomSheet(cmp: ComponentName, public val activity: ActivityInfo) : LinkSheetComponent(cmp)
    public class Interconnect(cmp: ComponentName, public val service: ServiceInfo) : LinkSheetComponent(cmp)
}
