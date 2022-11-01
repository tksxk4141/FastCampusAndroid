package com.example.aop_part3_chapter01

enum class NotificationType(val title: String, val id: Int) {
    NOMAL("일반 알림", 0),
    EXPENDABLE("확장형 알림", 1),
    CUSTOM("커스텀 알림", 3)

}