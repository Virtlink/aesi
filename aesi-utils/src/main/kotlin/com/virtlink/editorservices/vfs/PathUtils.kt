package com.virtlink.editorservices.vfs

object PathUtils {

    /**
     * Resolves the specified path relative to the specified resource.
     */
    fun resolve(path: Path, resource: IResource): IResource? {
        var currentFolder: IFolder? = resource as? IFolder ?: resource.parent
        var components = path.components
        while (currentFolder != null && components.isNotEmpty()) {
            val currentComponent = components.first()
            val currentName = currentComponent.name
            components = components.drop(1)

            currentFolder = when (currentComponent.kind) {
                PathComponentKind.Root -> currentFolder.project
                PathComponentKind.Current -> currentFolder
                PathComponentKind.Parent -> currentFolder.parent
                PathComponentKind.Folder -> currentFolder.children.filterIsInstance<IFolder>().firstOrNull { it -> it.name == currentName }
                PathComponentKind.File -> {
                    // Found it?
                    return currentFolder.children.filterIsInstance<IFile>().firstOrNull { it -> it.name == currentName }
                }
            }
        }
        return null
    }

    fun find(selector: (r: IResource) -> Boolean, filter: (f: IFolder) -> Boolean, depthFirst: Boolean, resource: IResource): List<IResource> {
        TODO()
    }
}