package com.nikolam.nsdkelper

import android.net.nsd.NsdServiceInfo
import java.lang.Exception


data class RegistrationFailedException(val msg : String) : Exception(msg)
data class UnRegistrationFailedException(val msg : String) : Exception(msg)
