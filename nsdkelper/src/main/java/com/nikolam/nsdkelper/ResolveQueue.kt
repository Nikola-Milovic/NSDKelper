package com.nikolam.nsdkelper

import android.net.nsd.NsdServiceInfo
import java.util.*

internal class ResolveQueue(nsdKelper: NsdKelper) {

    private val mNsdHelper: NsdKelper = nsdKelper

    private val tasks =
        LinkedList<NsdServiceInfo>()

    private var isRunning = false

    fun enqueue(serviceInfo: NsdServiceInfo) {
        tasks.add(serviceInfo)
        if (!isRunning) {
            isRunning = true
            run()
        }
    }

    operator fun next() {
        isRunning = true
        run()
    }

    private fun run() {
        val nsdServiceInfo = tasks.pollFirst()
        if (nsdServiceInfo != null) mNsdHelper.internalResolveService(nsdServiceInfo) else isRunning = false
    }

}