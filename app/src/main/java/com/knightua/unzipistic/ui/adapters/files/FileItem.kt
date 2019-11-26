package com.knightua.unzipistic.ui.adapters.files

import com.knightua.unzipistic.settings.Settings
import java.io.File

data class FileItem(val name: String, val size: Long) {

    private val MAX_TYPE_LENGTH = 8

    fun isArchive(): Boolean {
        when (getFormat()) {
            "rar",
            "zip" -> {
                return true
            }
        }

        return false
    }

    fun getFormat(): String {
        val nameParts = name.split(".")
        val type = nameParts[nameParts.size - 1]
        if (File(Settings.getDefaultPath() + "/" + name).isDirectory) {
            return "folder"
        }

        if (type.length >= MAX_TYPE_LENGTH) {
            return "file"
        }
        return type
    }
}