package com.virtlink.editorservices.codefolding

/**
 * Folding information.
 */
interface IFoldingInfo {
    /**
     * Gets a list of foldable regions.
     *
     * Folding regions may be nested in one another, but must not partially overlap.
     * The list must be ordered by start of the folded region.
     */
    val regions: List<IFoldingRegion>
}