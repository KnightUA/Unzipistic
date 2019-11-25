package com.knightua.unzipistic.ui.adapters.files

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.knightua.unzipistic.databinding.ItemFileBinding

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

    inner class ViewHolder(private var binding: ItemFileBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fileItem: FileItem, onItemClickListener: OnItemClickListener?) {
            binding.file = fileItem
            if (onItemClickListener != null) {
                binding.root.setOnClickListener {
                    onItemClickListener.onItemClick(layoutPosition)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}