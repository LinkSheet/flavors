rootProject.name = "flavors"
include("lib")

if (System.getenv("JITPACK")?.toBooleanStrictOrNull() != false) {
    include("testing")
}
