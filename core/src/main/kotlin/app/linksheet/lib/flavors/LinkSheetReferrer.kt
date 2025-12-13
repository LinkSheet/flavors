package app.linksheet.lib.flavors

import android.content.Intent
import android.net.Uri
import androidx.core.content.IntentCompat

public object LinkSheetReferrer {
    public const val BASE_PACKAGE_NAME: String = "fe.linksheet"
    public const val EXTRA_REFERRER: String = "$BASE_PACKAGE_NAME.extra.REFERRER"

    /**
     * Call this with the Intent LinkSheet sends to your Activity
     * to find out which app originally sent the Intent.
     */
    public fun getLinkSheetReferrer(intent: Intent): Uri? {
        return IntentCompat.getParcelableExtra(intent, EXTRA_REFERRER, Uri::class.java)
    }
}
