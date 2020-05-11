package com.nikolam.nsdkelper

import android.os.CountDownTimer


internal class DiscoveryTimer(
    private val timeoutListener: OnTimeoutListener,
    seconds: Long
) {

    lateinit var timer: CountDownTimer

    fun start() {
        timer.start()
    }

    fun cancel() {
        timer.cancel()
    }

    fun reset() {
        timer.cancel()
        timer.start()
    }

    fun timeout(seconds: Long) {
        timer.cancel()
        timer = createTimer(seconds)
    }

    private fun createTimer(seconds: Long): CountDownTimer {
        return object : CountDownTimer(1000 * seconds, 1000 * seconds) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                timeoutListener.onNsdDiscoveryTimeout()
            }
        }
    }

    internal interface OnTimeoutListener {
         fun onNsdDiscoveryTimeout()
    }

    init {
        timer = createTimer(seconds)
    }
}
