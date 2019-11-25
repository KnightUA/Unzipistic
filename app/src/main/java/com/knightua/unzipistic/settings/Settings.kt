package com.knightua.unzipistic.settings

import com.orhanobut.hawk.Hawk

object Settings {

    private const val DEFAULT_PATH = "default_path"

    fun saveDefaultPath(path: String?) {
        path?.let {
            Hawk.put(DEFAULT_PATH, it)
        }
    }

    fun getDefaultPath(): String {
        if (Hawk.contains(DEFAULT_PATH))
            return Hawk.get(DEFAULT_PATH)
        return ""
    }
}