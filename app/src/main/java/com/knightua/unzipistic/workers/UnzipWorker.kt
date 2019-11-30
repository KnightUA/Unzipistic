package com.knightua.unzipistic.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.knightua.unzipistic.databinding.models.FileItem
import com.knightua.unzipistic.managers.ProgressManager
import com.knightua.unzipistic.settings.Settings
import timber.log.Timber
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream

class UnzipWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private val progressManager =
        ProgressManager(context)

    override fun doWork(): Result {
        Timber.i("doWork: Start")

        val name = inputData.getString(FILE_NAME)
        val size = inputData.getLong(FILE_SIZE, 0)

        val model = FileItem(name!!, size)

        try {
            val buffer = ByteArray(1024)
            var zipEntry: ZipEntry?
            var count: Int

            val defaultPath = Settings.getDefaultPath()

            var maxSize = 0L
            var extractedSize = 0

            val zipArchive = ZipFile(defaultPath + "/" + model.name).entries()
            for (file in zipArchive)
                maxSize += file.size

            Timber.i("Max size: $maxSize")

            val inputStream = FileInputStream(defaultPath + "/" + model.name)
            val zipInputStream = ZipInputStream(BufferedInputStream(inputStream))

            while (zipInputStream.nextEntry.also { zipEntry = it } != null) {
                Timber.i("Unzipping ${zipEntry?.name}")
                if (zipEntry?.isDirectory!!) {
                    val fileMakeDirectory = File(defaultPath + "/" + zipEntry?.name)
                    fileMakeDirectory.mkdirs()
                    Timber.i("Make directory: ${fileMakeDirectory.path}")
                    continue
                }
                val fileOutputStream = FileOutputStream(defaultPath + "/" + zipEntry?.name)
                while (zipInputStream.read(buffer).also { count = it } != -1) {
                    extractedSize += buffer.size

                    var progress = extractedSize * 100 / maxSize
                    if (progress > 100)
                        progress = 100

                    progressManager.updateProgress(model, progress)

                    fileOutputStream.write(buffer, 0, count)
                }
                fileOutputStream.close()
                zipInputStream.closeEntry()
            }
            zipInputStream.close()
        } catch (exception: IOException) {
            exception.printStackTrace()
            return Result.failure()
        }

        progressManager.showFinish(model)
        Timber.i("doWork: End")
        return Result.success()
    }

    companion object {
        const val FILE_NAME = "file_name"
        const val FILE_SIZE = "file_size"
    }
}