package com.nikolam.nsdkelper

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo

class NsdKRegistrationListener(val nsdKelper: NsdKelper) : NsdManager.RegistrationListener{

    companion object {
        private const val ERROR_SOURCE = "android.net.nsd.NsdHelper.RegistrationListener"
    }

    override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo?, errorCode: Int) {
        throw UnRegistrationFailedException(ERROR_SOURCE)
    }

    override fun onServiceUnregistered(serviceInfo: NsdServiceInfo?) {
        nsdKelper.registerSuccessCallBack?.invoke(serviceInfo)
    }

    override fun onRegistrationFailed(serviceInfo: NsdServiceInfo?, errorCode: Int) {
        nsdKelper.registerFailureCallBack?.invoke(RegistrationFailedException(ERROR_SOURCE))
    }

    override fun onServiceRegistered(serviceInfo: NsdServiceInfo?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}