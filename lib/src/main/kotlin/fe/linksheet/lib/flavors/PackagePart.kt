package fe.linksheet.lib.flavors

interface PackagePart {
    val partName: String?

    val asPart: String
        get() = if (partName == null) "" else ".$partName"
}

