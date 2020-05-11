package com.nikolam.nsdkelper

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log

class NsdKRegistrationListener(val nsdKelper: NsdKelper) : NsdManager.RegistrationListener{

    companion object {
        private const val ERROR_SOURCE = "android.net.nsd.NsdHelper.RegistrationListener"
    }

    override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
        nsdKelper.logMessage("Service unregistration failed! Error code: $errorCode")
        nsdKelper.unRegisterFailureCallBack?.invoke(UnRegistrationFailedException(errorCode.toString()))
    }

    override fun onServiceUnregistered(serviceInfo: NsdServiceInfo) {
        nsdKelper.logMessage("Service unregistered ${serviceInfo.serviceName}")
        nsdKelper.unRegisterSuccessCallBack?.invoke(serviceInfo)
    }

    override fun onRegistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
        nsdKelper.logMessage("Service registration failed! Error code: $errorCode")
        nsdKelper.registerFailureCallBack?.invoke(RegistrationFailedException(errorCode.toString()))
    }

    override fun onServiceRegistered(serviceInfo: NsdServiceInfo) {
        nsdKelper.logMessage("Service unregistered ${serviceInfo.serviceName}")
        nsdKelper.registerSuccessCallBack?.invoke(serviceInfo)
    }

}