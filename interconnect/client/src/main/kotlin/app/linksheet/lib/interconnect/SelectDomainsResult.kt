package app.linksheet.lib.interconnect

public sealed class SelectDomainsResult {
    public data object ResultCanceled : SelectDomainsResult()
    public data object ResultConfirmed : SelectDomainsResult()
    public data class ResultPartial(val selectedDomains: List<String>) : SelectDomainsResult()
}
