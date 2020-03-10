package com.nikolam.nsdkelper

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo

class NsdKelper() {

    private val TAG = "NSDKelper"
    private lateinit var mNsdManager: NsdManager

    // Registration
    private var mRegistered = false
    private lateinit var mRegistrationListener: NsdKRegistrationListener
    private var mRegisteredServiceInfo = NsdServiceInfo()

    //
     var registerSuccessCallBack: ((NsdServiceInfo?) -> Unit)? = null
     var registerFailureCallBack: ((Exception?) -> Unit)? = null


    // Discovery
    private var mDiscoveryStarted = false
    private var mDiscoveryTimeout: Long = 15
    private var mDiscoveryServiceType: String? = null
    private var mDiscoveryServiceName: String? = null
    private lateinit var mDiscoveryListener: NsdKResolveListener
    //   private lateinit var mDiscoveryTimer: DiscoveryTimer

    // Resolve
    private var mAutoResolveEnabled = true
    private lateinit var mResolveQueue: ResolveQueue
    // Constructors

    /**
     * @param nsdManager Android [NsdManager].
     */
    constructor(nsdManager: NsdManager) : this() {
        mNsdManager = nsdManager
        mRegistrationListener = NsdKRegistrationListener(this)
        //  mDiscoveryTimer = DiscoveryTimer(this, mDiscoveryTimeout)
        // mResolveQueue = ResolveQueue(this)
    }

    /**
     * @param context Context is only needed to create [NsdManager] instance.
     */
    constructor(context: Context) : this() {
        mNsdManager =
            context.getSystemService(Context.NSD_SERVICE) as NsdManager
        mRegistrationListener = NsdKRegistrationListener(this)
        //  mDiscoveryTimer = DiscoveryTimer(this, mDiscoveryTimeout)
        //    mResolveQueue = ResolveQueue(this)
    }

    constructor(context: Context, nsdKRegistrationListener: NsdKRegistrationListener) : this() {
        mNsdManager = context.getSystemService(Context.NSD_SERVICE) as NsdManager
        mRegistrationListener = nsdKRegistrationListener
        //  mDiscoveryTimer = DiscoveryTimer(this, mDiscoveryTimeout)
        //    mResolveQueue = ResolveQueue(this)

    }

    fun registerService(
        sName: String,
        sType: String,
        sPort: Int,
        success: (NsdServiceInfo?) -> Unit,
        failure: (Exception?) -> Unit
    ) {
        val serviceInfo = NsdServiceInfo().apply {
            serviceName = sName
            serviceType = sType
            port = sPort
        }
        registerSuccessCallBack = success

        registerFailureCallBack = failure

        mNsdManager.registerService(
            serviceInfo,
            NsdManager.PROTOCOL_DNS_SD,
            mRegistrationListener
        )


    }

}

