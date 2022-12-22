package com.example.aop_part3_chapter06.chatlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aop_part3_chapter06.DBKey.Companion.CHILD_CHAT
import com.example.aop_part3_chapter06.DBKey.Companion.DB_USERS
import com.example.aop_part3_chapter06.R
import com.example.aop_part3_chapter06.chatdetail.ChatRoomActivity
import com.example.aop_part3_chapter06.databinding.FragmentChatlistBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatListFragment : Fragment(R.layout.fragment_chatlist) {
    private lateinit var chatlistAdapter : ChatListAdapter
    private lateinit var chatDB : DatabaseReference

    private var binding : FragmentChatlistBinding? = null
    private val auth : FirebaseAuth by lazy {
        Firebase.auth
    }
    private val chatRoomList = mutableListOf<ChatListItem>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentChatlistBinding = FragmentChatlistBinding.bind(view)
        binding = fragmentChatlistBinding

        chatlistAdapter = ChatListAdapter(onItemClicked = { chatRoom ->
            context?.let{
                val intent = Intent(it, ChatRoomActivity::class.java)
                intent.putExtra("chatKey", chatRoom.sellerId+chatRoom.buyerId)
                startActivity(intent)
            }
        })
        chatRoomList.clear()
        fragmentChatlistBinding.chatListRecyclerView.adapter = chatlistAdapter
        fragmentChatlistBinding.chatListRecyclerView.layoutManager = LinearLayoutManager(context)

        if(auth.currentUser==null){
            return
        }
        chatDB = Firebase.database.reference.child(DB_USERS).child(auth.currentUser!!.uid).child(CHILD_CHAT)
        chatDB.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    val model = it.getValue(ChatListItem::class.java)
                    model?:return

                    chatRoomList.add(model)
                }
                chatlistAdapter.submitList(chatRoomList)
                chatlistAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onResume() {
        super.onResume()
        chatlistAdapter.notifyDataSetChanged()
    }
}