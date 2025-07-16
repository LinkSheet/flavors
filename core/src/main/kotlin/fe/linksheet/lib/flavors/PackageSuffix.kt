package fe.linksheet.lib.flavors


public abstract class PackageSuffix(suffix: String?) {
    public val suffix: String = if (suffix == null) "" else ".$suffix"
}

public class Flavor(name: String?, public val priority: Int) : PackageSuffix(name) {
    public companion object {
        public val Default: Flavor = Flavor(null, 0)
    }
}

public class BuildType(public val name: String, public val priority: Int, suffix: String?) : PackageSuffix(suffix) {
    public companion object {
        public val Release: BuildType = BuildType("Release", 2, null)
        public val Nightly: BuildType = BuildType("Nightly", 1, "nightly")
        public val Debug: BuildType = BuildType("Debug", 0, "debug")
    }
}
