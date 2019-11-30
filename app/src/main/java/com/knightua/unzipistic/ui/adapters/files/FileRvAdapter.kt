package com.knightua.unzipistic.ui.adapters.files

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkInfo
import com.knightua.unzipistic.databinding.ItemFileBinding
import com.knightua.unzipistic.databinding.models.FileItem
import com.knightua.unzipistic.databinding.viewmodels.FileItemViewModel

class FileRvAdapter(
    private var items: ArrayList<FileItem>,
    private var listener: OnItemClickListener
) : RecyclerView.Adapter<FileRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFileBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position], listener)

    fun clearAndAddAll(fileItems: ArrayList<FileItem>) {
        items.clear()
        items.addAll(fileItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private var binding: ItemFileBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fileItem: FileItem, onItemClickListener: OnItemClickListener?) {
            binding.file = FileItemViewModel(fileItem)
            if (onItemClickListener != null) {
                binding.root.setOnClickListener {
                    onItemClickListener.onItemClick(binding.file?.model)
                }
                binding.imageButtonUnzip.setOnClickListener {
                    onItemClickListener.onActionClick(binding.file?.extractArchive())
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(fileModel: FileItem?)
        fun onActionClick(workInfo: LiveData<WorkInfo>?)
    }
}