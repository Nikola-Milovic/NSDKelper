package com.nikolam.nsdkelper

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo

class NsdKResolveListener(val nsdKelper : NsdKelper) : NsdManager.ResolveListener{
    override fun onResolveFailed(serviceInfo: NsdServiceInfo?, errorCode: Int) {
        nsdKelper.logMessage("Service failed to resolve! Error code: $errorCode")
        nsdKelper.onResolveFailureCallback?.invoke(OnResolveFailedException(errorCode.toString()))
    }

    override fun onServiceResolved(serviceInfo: NsdServiceInfo?) {
        nsdKelper.logMessage("Service resolved ${serviceInfo?.serviceName}")
        nsdKelper.onResolveSuccessCallback?.invoke(serviceInfo)
    }

}