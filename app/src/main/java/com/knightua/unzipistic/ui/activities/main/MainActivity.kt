package com.knightua.unzipistic.ui.activities.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.knightua.unzipistic.R
import com.knightua.unzipistic.databinding.ActivityMainBinding
import com.knightua.unzipistic.ui.adapters.files.FileRvAdapter

class MainActivity : AppCompatActivity(), FileRvAdapter.OnItemClickListener {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mBinding.viewModel = viewModel
        mBinding.executePendingBindings()

        mBinding.viewModel?.loadFiles()
        mBinding.recyclerViewFiles.adapter = FileRvAdapter(viewModel.fileItems, this)
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Clicked: $position position", Toast.LENGTH_LONG).show()
    }
}
