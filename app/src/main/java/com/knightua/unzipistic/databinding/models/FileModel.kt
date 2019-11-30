package com.knightua.unzipistic.databinding.models

import com.knightua.unzipistic.settings.Settings
import io.reactivex.rxjava3.core.Single
import timber.log.Timber
import java.io.File

class FileModel {

    fun getFilesFromDefaultPath(): Single<ArrayList<FileItem>> {
        return Single.create { emitter ->
            val fileItems = ArrayList<FileItem>()
            val path = Settings.getDefaultPath()
            val directory = File(path)
            Timber.d("directory: %s", directory.isDirectory.toString())
            val files = directory.listFiles()
            files?.let {
                Timber.d("Size: %s", files.size)
                for (file in files) {
                    Timber.d("FileName: %s Size: %d bytes", file.name, file.length())
                    fileItems.add(
                        FileItem(
                            file.name,
                            file.length()
                        )
                    )
                }
            }
            emitter.onSuccess(fileItems)
        }
    }

}