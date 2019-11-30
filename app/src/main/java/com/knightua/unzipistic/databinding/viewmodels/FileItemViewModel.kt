package com.knightua.unzipistic.databinding.viewmodels

import androidx.lifecycle.LiveData
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.knightua.unzipistic.databinding.models.FileItem
import com.knightua.unzipistic.workers.UnzipWorker


class FileItemViewModel(val model: FileItem) {

    fun extractArchive(): LiveData<WorkInfo> {
        val workData = Data.Builder()
            .putString(UnzipWorker.FILE_NAME, model.name)
            .putLong(UnzipWorker.FILE_SIZE, model.size)
            .build()

        val workRequest =
            OneTimeWorkRequest.Builder(UnzipWorker::class.java).setInputData(workData).build()
        WorkManager.getInstance().enqueue(workRequest)
        return WorkManager.getInstance().getWorkInfoByIdLiveData(workRequest.id)
    }
}