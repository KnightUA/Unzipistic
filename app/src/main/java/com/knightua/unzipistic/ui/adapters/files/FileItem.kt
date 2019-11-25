package com.knightua.unzipistic.ui.adapters.files

data class FileItem(val name: String, val size: Long) {

    fun isArchive(): Boolean {
        val nameParts = name.split(".")
        val type = nameParts[nameParts.size - 1]
        when (type) {
            "rar",
            "zip" -> {
                return true
            }
        }

        return false
    }
}