package fe.linksheet.lib.flavors


sealed class LinkSheetApp private constructor(
    basePackage: String,
    flavors: Set<Flavor> = setOf(Flavor.Default),
    buildTypes: Set<BuildType> = setOf(BuildType.Default, BuildType.Debug, NightlyBuild),
    signatures: Set<Signature> = setOf(Signature, SignatureCI),
) : AndroidApp(basePackage, flavors, buildTypes, signatures) {

    companion object {
        val NightlyBuild = BuildType("nightly")
        val Signature = Signature("C2A8B18C328DFB39C896491757ED11C145D3ACCA43212FA3DE362433C416AAA9")
        val SignatureCI = Signature("3FCF7675BC90E239892C7262499DCC9F8CE6A52B7E58D02B56AA60CA669D6701")
    }

    data object LinkSheet : LinkSheetApp(
        "fe.linksheet",
        flavors = setOf(Flavor.Default, Flavor("pro")),
    )

    data object Compat : LinkSheetApp(
        "fe.linksheet.compat",
        buildTypes = setOf(BuildType.Default, BuildType.Debug),
    )

    data object Assist : LinkSheetApp("fe.linksheet.assist")
}
