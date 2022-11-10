package com.example.aop_part3_chapter03

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        createNotificatonChannel(context)
        notifyNotification(context)
    }

    private fun createNotificatonChannel(context: Context){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val notificationchannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "기상 알람",
                NotificationManager.IMPORTANCE_HIGH
            )
            NotificationManagerCompat.from(context).createNotificationChannel(notificationchannel)
        }
    }
    private fun notifyNotification(context: Context){
        with(NotificationManagerCompat.from(context)){
            val build = NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID)
                .setContentTitle("알람")
                .setContentText("일어날 시간입니다.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            notify(NOTIFICATION_ID, build.build())
        }

    }

    companion object{
        const val NOTIFICATION_ID = 100
        const val NOTIFICATION_CHANNEL_ID = "1000"
    }
}