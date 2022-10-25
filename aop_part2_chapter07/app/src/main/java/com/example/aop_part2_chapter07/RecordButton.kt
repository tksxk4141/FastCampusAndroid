package com.example.aop_part2_chapter07

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton

class RecordButton(
    context: Context,
    attrs: AttributeSet
) : AppCompatImageButton(context, attrs) {

    init{
        setBackgroundResource(R.drawable.shape_oval_button)
    }

    fun updateIconWithState(state: State){
        when(state){
            State.BEFORE_RECODING->{
                setImageResource(R.drawable.ic_record)
            }
            State.ON_RECODING ->{
                setImageResource(R.drawable.ic_stop)
            }
            State.AFTER_RECODING->{
                setImageResource(R.drawable.ic_play)
            }
            State.ON_PLAYING->{
                setImageResource(R.drawable.ic_stop)
            }
        }
    }
}