package com.knightua.unzipistic.ui.activities.directory

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import timber.log.Timber
import java.io.File


class DirectoryChooserViewModel : ViewModel() {

    private lateinit var mChooseDirectory: ChooseDirectory

    val isPermissionsGranted = ObservableBoolean(false)

    fun setChooseDirectory(chooseDirectory: ChooseDirectory) {
        mChooseDirectory = chooseDirectory
    }

    fun chooseDefaultDirectory() {
        Timber.i("openFileExplorer")

        if (::mChooseDirectory.isInitialized) {
            mChooseDirectory.openFileExplorer()
        }
    }

    fun saveSelectedPath(path: String?) {
        Timber.i("Path: $path")

        getAllFilesInPath(path!!)
    }

    private fun getAllFilesInPath(path: String) {
        val directory = File(path)
        Timber.d("directory: %s", directory.isDirectory.toString())
        val files = directory.listFiles()
        files?.let {
            Timber.d("Size: %s", files.size)
            for (file in files) {
                Timber.d("FileName: %s Size: %d bytes", file.name, file.length())
            }
        }
    }

    interface ChooseDirectory {
        fun openFileExplorer()
    }
}