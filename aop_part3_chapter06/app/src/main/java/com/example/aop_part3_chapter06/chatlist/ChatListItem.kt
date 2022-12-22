package com.example.aop_part3_chapter06.chatlist

data class ChatListItem(
    val buyerId: String,
    val sellerId: String,
    val itemTitle: String,
    val key: String
){
    constructor(): this("","","","")
}
