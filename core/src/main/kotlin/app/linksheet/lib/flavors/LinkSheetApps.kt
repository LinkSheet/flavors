package app.linksheet.lib.flavors


public abstract class LinkSheetApp(
    basePackage: String,
    flavors: Set<Flavor> = setOf(Flavor.Default),
    buildTypes: Set<BuildType> = setOf(BuildType.Release, BuildType.Debug, BuildType.Nightly),
    signatures: Set<Signature> = setOf(Signature._1fexd, Signature._1fexdCI),
) : AndroidApp(basePackage, flavors, buildTypes, signatures) {

    public companion object {
    }

    public typealias LinkSheet = app.linksheet.lib.flavors.LinkSheet

    public data object Compat : LinkSheetApp(
        basePackage = "fe.linksheet.compat",
        buildTypes = setOf(BuildType.Release, BuildType.Debug),
    )

    public data object Assist : LinkSheetApp(
        basePackage = "app.linksheet.assist"
    )
}
