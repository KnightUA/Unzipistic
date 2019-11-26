package com.knightua.unzipistic.databinding

import android.widget.TextView
import androidx.databinding.BindingAdapter

object FileItemDataBindingAdapter {

    @JvmStatic
    @BindingAdapter("showFileSize")
    fun showFileSize(view: TextView, size: Long) {
        var index = 0
        var newSize = size.toFloat()

        do {
            newSize /= 1024
            index++
        } while (newSize >= 1024)

        view.text = String.format("%.2f %s", newSize, SIZES[index])
    }

    private val SIZES = arrayListOf<String>("B", "KB", "MB", "GB")
}