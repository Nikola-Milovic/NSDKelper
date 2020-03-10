package com.nikolam.nsdkelper

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo

class NsdKDiscoveryListener : NsdManager.DiscoveryListener{
    override fun onServiceFound(serviceInfo: NsdServiceInfo?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStopDiscoveryFailed(serviceType: String?, errorCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartDiscoveryFailed(serviceType: String?, errorCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDiscoveryStarted(serviceType: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDiscoveryStopped(serviceType: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onServiceLost(serviceInfo: NsdServiceInfo?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}