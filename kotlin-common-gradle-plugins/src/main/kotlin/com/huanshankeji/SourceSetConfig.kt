package com.huanshankeji

// TODO use or remove
// TODO How should dependents be added, the "implementation" way or "api" way? Does this make this class too complicated and unnecessary as one can just call the Gradle functions directly?
class SourceSetConfig(val type: Type, val name: String, val dependents: List<String>) {

    /**
     * @see SourceSetType
     */
    enum class Type {
        Existing, New
    }

    companion object {
        val main = SourceSetConfig(Type.Existing, "main", emptyList())
    }
}