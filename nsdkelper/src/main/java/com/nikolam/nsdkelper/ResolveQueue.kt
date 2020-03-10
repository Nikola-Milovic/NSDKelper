package com.nikolam.nsdkelper

import android.net.nsd.NsdServiceInfo
import java.util.*

internal class ResolveQueue(nsdKelper: NsdKelper) {

    private val mNsdHelper: NsdKelper = nsdKelper

    private val mTasks =
        LinkedList<NsdServiceInfo>()

    private var mIsRunning = false

    fun enqueue(serviceInfo: NsdServiceInfo) {
        mTasks.add(serviceInfo)
        if (!mIsRunning) {
            mIsRunning = true
            run()
        }
    }

    operator fun next() {
        mIsRunning = true
        run()
    }

    private fun run() {
        val nsdServiceInfo = mTasks.pollFirst()
//        if (nsdServiceInfo != null) mNsdHelper.resolveService(nsdServiceInfo) else mIsRunning =
//            false
    }

}