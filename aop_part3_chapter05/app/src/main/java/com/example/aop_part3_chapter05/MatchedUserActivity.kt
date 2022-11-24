package com.example.aop_part3_chapter05

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aop_part3_chapter05.DBKey.Companion.LIKED_BY
import com.example.aop_part3_chapter05.DBKey.Companion.NAME
import com.example.aop_part3_chapter05.DBKey.Companion.USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MatchedUserActivity: AppCompatActivity() {
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var userDB : DatabaseReference
    private val adapter = MatchedUserAdapter()
    private val cardItems = mutableListOf<CardItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        userDB = Firebase.database.reference.child(USERS)

        initMatchedRecyclerView()
        getMatchUsers()
    }

    private fun initMatchedRecyclerView(){
        val recyclerview = findViewById<RecyclerView>(R.id.matchedUserRecyclerView)

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter
    }
    private fun getMatchUsers(){
        val matchedDB = userDB.child(getCurrentUserID()).child(LIKED_BY).child("match")

        matchedDB.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if(snapshot.key?.isNotEmpty()==true){
                    getUserByKey(snapshot.key.orEmpty())
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    private fun getUserByKey(userId : String){
        userDB.child(userId).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                cardItems.add(CardItem(userId,snapshot.child(NAME).value.toString()))
                adapter.submitList(cardItems)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    private fun getCurrentUserID() : String {
        if(auth.currentUser ==null){
            Toast.makeText(this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
        return auth.currentUser?.uid.orEmpty()
    }

}