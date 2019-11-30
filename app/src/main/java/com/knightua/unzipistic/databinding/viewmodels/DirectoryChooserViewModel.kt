package com.knightua.unzipistic.databinding.viewmodels

import androidx.lifecycle.ViewModel
import com.knightua.unzipistic.settings.Settings
import timber.log.Timber


class DirectoryChooserViewModel : ViewModel() {

    private lateinit var mChooseDirectory: ChooseDirectory

    fun setChooseDirectory(chooseDirectory: ChooseDirectory) {
        mChooseDirectory = chooseDirectory
    }

    fun chooseDefaultDirectory() {
        Timber.i("openFileExplorer")

        if (::mChooseDirectory.isInitialized) {
            mChooseDirectory.openFileExplorer()
        }
    }

    fun handleSelectedPath(path: String?) {
        Timber.i("Path: $path")
        Settings.saveDefaultPath(path)

        if (::mChooseDirectory.isInitialized) {
            mChooseDirectory.openNextScreen()
        }
    }

    interface ChooseDirectory {
        fun openFileExplorer()
        fun openNextScreen()
    }
}