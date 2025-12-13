package fe.linksheet.interconnect;

import android.content.ComponentName;
import fe.linksheet.interconnect.IDomainSelectionResultCallback;
import fe.linksheet.interconnect.ISelectedDomainsCallback;
import fe.linksheet.interconnect.StringParceledListSlice;

interface ILinkSheetService {
    void getSelectedDomainsAsync(String packageName, in ISelectedDomainsCallback callback) = 3;

    // Request that the passed domains be set as preferred for the
    // passed package.
    // The ComponentName should point to the Activity handling the domains.
    // Currently, the passed package must match the calling package.
    void selectDomains(
        String packageName,
        in StringParceledListSlice domains,
        in ComponentName componentName
    ) = 2;

    void selectDomainsWithCallback(
        String packageName,
        in StringParceledListSlice domains,
        in ComponentName componentName,
        in IDomainSelectionResultCallback callback
    ) = 4;
}
