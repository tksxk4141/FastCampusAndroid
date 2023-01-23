package com.example.aop_part5_chapter04.chatdetail

data class ChatItem(
    val senderId: String,
    val message: String
){
    constructor() : this("","")
}
