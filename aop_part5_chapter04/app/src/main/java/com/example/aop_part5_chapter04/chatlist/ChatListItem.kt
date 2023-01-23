package com.example.aop_part5_chapter04.chatlist

data class ChatListItem(
    val buyerId: String,
    val sellerId: String,
    val itemTitle: String,
    val key: String
){
    constructor(): this("","","","")
}
