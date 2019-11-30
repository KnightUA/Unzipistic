package com.knightua.unzipistic.ui.activities.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.work.WorkInfo
import com.knightua.unzipistic.R
import com.knightua.unzipistic.databinding.ActivityMainBinding
import com.knightua.unzipistic.databinding.models.FileItem
import com.knightua.unzipistic.databinding.viewmodels.MainViewModel
import com.knightua.unzipistic.ui.adapters.files.FileRvAdapter

class MainActivity : AppCompatActivity(), FileRvAdapter.OnItemClickListener {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAdapter: FileRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mBinding.viewModel = viewModel
        mBinding.executePendingBindings()

        mBinding.viewModel?.loadFiles()
        mAdapter = FileRvAdapter(viewModel.fileItems, this)
        mBinding.recyclerViewFiles.adapter = mAdapter

    }

    override fun onItemClick(fileModel: FileItem?) {
        Toast.makeText(this, "Clicked: ${fileModel?.name}", Toast.LENGTH_LONG).show()
    }

    override fun onActionClick(workInfo: LiveData<WorkInfo>?) {
        workInfo?.observe(this, Observer {
            if (it.state.isFinished) {
                mBinding.viewModel?.loadFiles()
                mAdapter.clearAndAddAll(mBinding.viewModel?.fileItems!!)
            }
        })
    }
}
