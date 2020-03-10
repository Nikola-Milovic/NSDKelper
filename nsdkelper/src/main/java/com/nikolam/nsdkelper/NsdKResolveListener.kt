package com.nikolam.nsdkelper

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo

class NsdKResolveListener : NsdManager.ResolveListener{
    override fun onResolveFailed(serviceInfo: NsdServiceInfo?, errorCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onServiceResolved(serviceInfo: NsdServiceInfo?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}