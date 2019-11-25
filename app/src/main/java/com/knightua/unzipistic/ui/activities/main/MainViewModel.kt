package com.knightua.unzipistic.ui.activities.main

import androidx.lifecycle.ViewModel
import com.knightua.unzipistic.ui.adapters.files.FileItem
import com.knightua.unzipistic.ui.adapters.files.FileModel
import timber.log.Timber

class MainViewModel : ViewModel() {

    var fileModel = FileModel()
    var fileItems = ArrayList<FileItem>()

    fun loadFiles() {
        fileModel.getFilesFromDefaultPath().subscribe({
            fileItems = it
        }, {
            Timber.e(it)
        })
    }
}