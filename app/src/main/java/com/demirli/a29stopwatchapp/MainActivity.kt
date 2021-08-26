package com.demirli.a29stopwatchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var runnable: Runnable
    private lateinit var handler: Handler

    private var second = 0
    private var minute = 0
    private var hour = 0

    private var delay: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runnable = Runnable {}
        handler = Handler()

        start_btn.setOnClickListener {

            delayControl()
            setClock(delay)
            startedState()
        }

        stop_btn.setOnClickListener {

            handler.removeCallbacks(runnable)
            stoppedState()
        }

        reset_btn.setOnClickListener {

            second = 0
            minute = 0
            hour = 0
            textView.text = "%02d".format(hour) + ":" + "%02d".format(minute) + ":" + "%02d".format(second)

            handler.removeCallbacks(runnable)
            stoppedState()
        }
    }

    fun delayControl(){
        if(delay_et.text.toString() != ""){
            delay = delay_et.text.toString().toLong()
        }
    }

    fun startedState(){
        start_btn.visibility = View.INVISIBLE
        stop_btn.visibility = View.VISIBLE
    }
    fun stoppedState(){
        start_btn.visibility = View.VISIBLE
        stop_btn.visibility = View.INVISIBLE
    }

    private fun setClock (delay: Long){
        runnable = object: Runnable{
            override fun run() {
                second++

                if(second == 60){
                    second = 0
                    minute++
                    if(minute == 60){
                        second = 0
                        minute = 0
                        hour++
                        textView.text = "%02d".format(hour) + ":" + "%02d".format(minute) + ":" + "%02d".format(second)
                    }else{
                        textView.text = "%02d".format(hour) + ":" + "%02d".format(minute) + ":" + "%02d".format(second)
                    }
                }else if(hour == 24){
                    second = 0
                    minute = 0
                    hour = 0
                    textView.text = "%02d".format(hour) + ":" + "%02d".format(minute) + ":" + "%02d".format(second)
                }else{
                    textView.text = "%02d".format(hour) + ":" + "%02d".format(minute) + ":" + "%02d".format(second)
                }
                handler.postDelayed(runnable,delay)
            }
        }
        handler.postDelayed(runnable,delay)
    }
}
