package com.nikolam.nsdkelper

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log

typealias Success = (NsdServiceInfo?) -> Unit
typealias Failure = (java.lang.Exception) -> Unit

class NsdKelper() {

    private val TAG = "NSDKelper"
    private lateinit var mNsdManager: NsdManager

    // Registration
    private var mRegistered = false
    private lateinit var registrationListener: NsdManager.RegistrationListener


    internal var registerSuccessCallBack: Success? = null
    internal var registerFailureCallBack: Failure? = null

    internal  var unRegisterSuccessCallBack: Success? = null
    internal var unRegisterFailureCallBack: Failure? = null


    // Discovery
    private var discoveryStarted = false
    private var mDiscoveryTimeout: Long = 15


    private lateinit var discoveryListener: NsdManager.DiscoveryListener

    //   private lateinit var mDiscoveryTimer: DiscoveryTimer
    internal  var onServiceFoundCallback: Success? = null
    internal  var onServiceLostCallback: Success? = null
    internal  var onDiscoveryFailure: Failure? = null

    // Resolve
    private var mAutoResolveEnabled = true
    private lateinit var resolveQueue: ResolveQueue
    private lateinit var resolveListener: NsdManager.ResolveListener

    internal var onResolveSuccessCallback: Success? = null
    internal var onResolveFailureCallback: Failure? = null

    // Common
    private var loggingEnabled = false

    /**
     * @param context Context is only needed to create [NsdManager] instance.
     */
    constructor(context: Context) : this() {
        mNsdManager =
            context.getSystemService(Context.NSD_SERVICE) as NsdManager
        registrationListener = NsdKRegistrationListener(this)
        discoveryListener = NsdKDiscoveryListener(this)
        resolveListener = NsdKResolveListener(this)

    }

    constructor(context: Context, registrationListener: NsdManager.RegistrationListener? = null, discoveryListener: NsdManager.DiscoveryListener? = null, resolveListener: NsdManager.ResolveListener? = null) : this() {
        mNsdManager = context.getSystemService(Context.NSD_SERVICE) as NsdManager
        this.registrationListener = registrationListener ?: NsdKRegistrationListener(this)
        this.discoveryListener = discoveryListener ?: NsdKDiscoveryListener(this)
        this.resolveListener = resolveListener ?: NsdKResolveListener(this)
    }


    /**
     * @return are logcat messages enabled.
     */
    fun isLogEnabled(): Boolean {
        return loggingEnabled
    }

    /**
     * Enable or disable logcat messages.
     * By default it's disabled.
     *
     * @param isLogEnabled If true logcat messages are enabled.
     */
    fun setLogEnabled(isLogEnabled: Boolean) {
        loggingEnabled = isLogEnabled
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
            registrationListener
        )
    }

    fun unRegisterService(
        success: (NsdServiceInfo?) -> Unit,
        failure: (Exception?) -> Unit
    ) {

        unRegisterFailureCallBack = failure
        unRegisterSuccessCallBack = success

        mNsdManager.unregisterService(registrationListener)
    }

    fun stopServiceDiscovery() {
        Log.d("TAG", "Stopped discovery")
        mNsdManager.stopServiceDiscovery(discoveryListener)


    }


    fun startServiceDiscovery(
        serviceType: String,
        serviceFound: Success,
        serviceLost: Success,
        failure: Failure
    ) {
        onServiceFoundCallback = serviceFound

        onServiceLostCallback = serviceLost

        onDiscoveryFailure = failure

        mNsdManager.discoverServices(
            serviceType,
            NsdManager.PROTOCOL_DNS_SD,
            discoveryListener
        )

    }

    fun onNsdServiceResolved(resolvedService: NsdServiceInfo) {
     //   resolveQueue.next()
        //  mDiscoveryTimer.reset()

    }

    fun resolveService(nsdServiceInfo: NsdServiceInfo?, success : Success, failure: Failure) {

        onResolveSuccessCallback = success

        onResolveFailureCallback = failure

        mNsdManager.resolveService(nsdServiceInfo, resolveListener)
    }


    /// Internal

    internal fun logMessage(message : String){
        if (loggingEnabled){
            Log.d( TAG, message)
        }
    }

}

