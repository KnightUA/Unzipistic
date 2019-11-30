package com.knightua.unzipistic.databinding.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.knightua.unzipistic.R

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

        view.text = String.format(
            "%.2f %s",
            newSize,
            view.resources.getStringArray(R.array.file_sizes)[index]
        )
    }
}