package com.knightua.unzipistic.ui.activities.directory

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.knightua.unzipistic.R
import com.knightua.unzipistic.databinding.ActivityDirectoryChooserBinding
import com.knightua.unzipistic.utils.FileUtil


class DirectoryChooserActivity : AppCompatActivity(), DirectoryChooserViewModel.ChooseDirectory {

    private lateinit var mBinding: ActivityDirectoryChooserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_directory_chooser)
        val viewModel = ViewModelProviders.of(this).get(DirectoryChooserViewModel::class.java)
        mBinding.viewModel = viewModel
        mBinding.executePendingBindings()

        mBinding.viewModel?.setChooseDirectory(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_DIRECTORY_PATH && resultCode == Activity.RESULT_OK) {
            val path = FileUtil.getFullPathFromTreeUri(data?.data, this)
            mBinding.viewModel?.saveSelectedPath(path)
            //mBinding.viewModel?.saveSelectedPath(this.getExternalFilesDir(null)?.path)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_STORAGE_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                openFileExplorer()
        }
    }

    override fun openFileExplorer() {
        if (checkPermissions()) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
            startActivityForResult(intent, REQUEST_DIRECTORY_PATH)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_STORAGE_PERMISSIONS
            )
        }
    }

    private fun checkPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        } else {
            return true
        }
    }

    companion object {
        const val REQUEST_STORAGE_PERMISSIONS = 9009
        const val REQUEST_DIRECTORY_PATH = 1001
    }
}
