package com.nikolam.nsdkelper

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log

class NsdKDiscoveryListener(private val nsdKelper: NsdKelper) : NsdManager.DiscoveryListener{

    override fun onDiscoveryStarted(regType: String) {
        nsdKelper.logMessage("Service discovery started.")
    }

    override fun onServiceFound(service: NsdServiceInfo) {
        nsdKelper.onServiceFoundCallback?.invoke(service)
        nsdKelper.logMessage("Service found ${service.serviceName}")
    }

    override fun onServiceLost(service: NsdServiceInfo) {
        nsdKelper.onServiceLostCallback?.invoke(service)
        nsdKelper.logMessage("Service lost ${service.serviceName}")
    }

    override fun onDiscoveryStopped(serviceType: String) {
        nsdKelper.logMessage("Service discovery stopped.")
    }

    override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
        nsdKelper.onDiscoveryFailure?.invoke(OnStartDiscoveryFailedException(errorCode.toString()))
        nsdKelper.logMessage("Starting service discovery failed! Error code: $errorCode")
        nsdKelper.stopServiceDiscovery()
    }

    override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
        nsdKelper.onDiscoveryFailure?.invoke(OnStopDiscoveryFailedException(errorCode.toString()))
        nsdKelper.stopServiceDiscovery()
        nsdKelper.logMessage("Stopping service discovery failed! Error code: $errorCode")
    }
}