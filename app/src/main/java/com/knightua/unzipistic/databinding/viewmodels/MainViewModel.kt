package com.knightua.unzipistic.databinding.viewmodels

import androidx.lifecycle.ViewModel
import com.knightua.unzipistic.databinding.models.FileItem
import com.knightua.unzipistic.databinding.models.FileModel
import timber.log.Timber

class MainViewModel : ViewModel() {

    var fileModel =
        FileModel()
    var fileItems = ArrayList<FileItem>()

    fun loadFiles() {
        fileModel.getFilesFromDefaultPath().subscribe({
            fileItems = it
        }, {
            Timber.e(it)
        })
    }
}