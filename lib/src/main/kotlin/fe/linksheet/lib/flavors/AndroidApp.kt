package fe.linksheet.lib.flavors

abstract class AndroidApp(
    basePackage: String,
    flavors: Set<Flavor>,
    buildTypes: Set<BuildType>,
    signatures: Set<Signature>,
) {
    private val packageNames = buildMap {
        for (flavor in flavors) {
            for (buildType in buildTypes) {
                put("$basePackage${flavor.asPart}${buildType.asPart}", flavor to buildType)
            }
        }
    }

    private val signatures = signatures.mapTo(LinkedHashSet()) { it.hexFingerprint.encodeToByteArray() }

    fun isApp(`package`: String): Pair<Flavor, BuildType>? {
        return packageNames[`package`]
    }

    fun isValidSignature(signature: ByteArray): Boolean {
        return signature in signatures
    }
}

@JvmInline
value class Signature(val hexFingerprint: String) {
    companion object {
        private val hexRegex = Regex("[A-Za-z0-9]{64}")
    }

    init {
        require(hexFingerprint.matches(hexRegex)) { "Fingerprint must be a hex string!" }
    }
}

@JvmInline
value class BuildType(private val name: String?) : PackagePart {
    companion object {
        val Default = BuildType(null)
        val Debug = BuildType("debug")
    }

    override val partName: String?
        get() = name
}


@JvmInline
value class Flavor(private val name: String?) : PackagePart {
    companion object {
        val Default = Flavor(null)
    }

    override val partName: String?
        get() = name
}
