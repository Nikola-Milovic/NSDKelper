package com.nikolam.nsdkelper

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log

typealias Success = (NsdServiceInfo) -> Unit
typealias Failure = (java.lang.Exception) -> Unit

class NsdKelper() : DiscoveryTimer.OnTimeoutListener {

    private val TAG = "NSDKelper"
    private lateinit var mNsdManager: NsdManager

    // Registration
    private var mRegistered = false
    private lateinit var registrationListener: NsdManager.RegistrationListener

    lateinit var registeredServiceInfo : NsdServiceInfo


    internal var registerSuccessCallBack: Success? = null
    internal var registerFailureCallBack: Failure? = null

    internal  var unRegisterSuccessCallBack: Success? = null
    internal var unRegisterFailureCallBack: Failure? = null


    // Discovery
    private var discoveryStarted = false
    private var discoveryTimeout: Long = 7

    private lateinit var discoveryTimer : DiscoveryTimer

    internal var serviceTypeToDiscover : String = "_http._tcp"
    internal var discoveryServiceName : String? = ""


    private lateinit var discoveryListener: NsdManager.DiscoveryListener

    internal  var onServiceFoundCallback: Success? = null
    internal  var onServiceLostCallback: Success? = null
    internal  var onDiscoveryFailure: Failure? = null

    // Resolve
    private var autoResolveEnabled = true
    private lateinit var resolveQueue: ResolveQueue

    private lateinit var resolveListener: NsdManager.ResolveListener

    internal var onResolveSuccessCallback: Success? = null
    internal var onResolveFailureCallback: Failure? = null

    /**
     * Whether or not logcat messages are enabled
     */
     var loggingEnabled = false


    /**
     * @param context Context is only needed to create [NsdManager] instance.
     */
    constructor(context: Context) : this() {
        mNsdManager =
            context.getSystemService(Context.NSD_SERVICE) as NsdManager
        discoveryTimer = DiscoveryTimer(this, discoveryTimeout)
        resolveQueue = ResolveQueue(this)

    }

    fun registerService(
        sName: String,
        sType: String,
        sPort: Int,
        success: Success,
        failure: Failure
    ) {
        if(sPort == 0) return

        registeredServiceInfo = NsdServiceInfo()
        registeredServiceInfo.serviceName = sName
        registeredServiceInfo.serviceType = sType
        registeredServiceInfo.port = sPort

        val serviceInfo = NsdServiceInfo().apply {
            serviceName = sName
            serviceType = sType
            port = sPort
        }

        // add findAvaiablePort()
        registerSuccessCallBack = success

        registerFailureCallBack = failure

        registrationListener = NsdKRegistrationListener(this)



        mNsdManager.registerService(
            serviceInfo,
            NsdManager.PROTOCOL_DNS_SD,
            registrationListener
        )
    }

    fun unRegisterService(
        success: Success,
        failure: Failure
    ) {

        unRegisterFailureCallBack = failure
        unRegisterSuccessCallBack = success

        mNsdManager.unregisterService(registrationListener)
    }

    fun stopServiceDiscovery() {
        if (discoveryStarted) {
            discoveryStarted = false
            discoveryTimer.cancel()
            Log.d(TAG, discoveryListener.toString())
            mNsdManager.stopServiceDiscovery(discoveryListener)
        }
    }


    fun startServiceDiscovery(
        serviceType: String,
        serviceName : String?,
        serviceFound: Success,
        serviceLost: Success,
        failure: Failure
    ) {
        if(!discoveryStarted) {

            serviceTypeToDiscover = serviceType
            discoveryServiceName = serviceName

            onServiceFoundCallback = serviceFound

            onServiceLostCallback = serviceLost

            onDiscoveryFailure = failure

            discoveryStarted = true

            discoveryTimer.start()

            discoveryListener = NsdKDiscoveryListener(this)

            Log.d(TAG, discoveryListener.toString())

            mNsdManager.discoverServices(
                serviceType,
                NsdManager.PROTOCOL_DNS_SD,
                discoveryListener
            )
        }

    }

    fun onNsdServiceResolved() {
        resolveQueue.next()
        discoveryTimer.reset()
    }

    fun resolveService(nsdServiceInfo: NsdServiceInfo, success : Success, failure: Failure) {

        onResolveSuccessCallback = success

        onResolveFailureCallback = failure

        resolveListener = NsdKResolveListener(this)

        //mNsdManager.resolveService(nsdServiceInfo, resolveListener)
        resolveQueue.enqueue(nsdServiceInfo)
    }

     ////////////////////////
    ///     Internal     ///
   ////////////////////////


    internal fun internalResolveService(nsdServiceInfo: NsdServiceInfo) {
        mNsdManager.resolveService(nsdServiceInfo, NsdKResolveListener(this))
    }


    internal fun logMessage(message : String){
        if (loggingEnabled){
            Log.d( TAG, message)
        }
    }

    override fun onNsdDiscoveryTimeout() {
        stopServiceDiscovery()
    }

}

