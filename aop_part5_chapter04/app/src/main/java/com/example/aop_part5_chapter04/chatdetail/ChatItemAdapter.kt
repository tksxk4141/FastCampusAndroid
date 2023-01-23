package com.example.aop_part5_chapter04.chatdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aop_part5_chapter04.databinding.ItemChatBinding

class ChatItemAdapter: ListAdapter<ChatItem, ChatItemAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding : ItemChatBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(ChatItem: ChatItem){
            binding.senderTextView.text = ChatItem.senderId
            binding.messageTextView.text = ChatItem.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChatBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<ChatItem>(){
            override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}