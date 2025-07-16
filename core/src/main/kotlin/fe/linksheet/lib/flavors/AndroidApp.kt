package fe.linksheet.lib.flavors

public abstract class AndroidApp(
    public val basePackage: String,
    flavors: Set<Flavor>,
    buildTypes: Set<BuildType>,
    signatures: Set<Signature>,
) {
    public val packageNames: Map<String, App> = buildMap {
        for (flavor in flavors) {
            for (buildType in buildTypes) {
                val app = App(
                    "$basePackage${flavor.suffix}${buildType.suffix}",
                    flavor,
                    buildType,
                    flavor.priority * 10 + buildType.priority
                )
                put(app.packageName, app)
            }
        }
    }

    public val sortedApps: List<App> by lazy {
        packageNames.values.sortedByDescending { it.priority }
    }

    private val signatures = signatures.associateBy { it.fingerprint.lowercase() }

    public fun isApp(`package`: String): App? {
        return packageNames[`package`]
    }

    public fun isValidSignature(fingerprint: String): Signature? {
        return signatures[fingerprint.lowercase()]
    }
}

public data class App(val packageName: String, val flavor: Flavor, val buildType: BuildType, val priority: Int)
