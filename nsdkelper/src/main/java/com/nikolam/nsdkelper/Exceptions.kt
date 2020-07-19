package com.nikolam.nsdkelper

import java.lang.Exception


data class RegistrationFailedException(val code : String) : Exception(code)
data class UnRegistrationFailedException(val code : String) : Exception(code)


data class OnStartDiscoveryFailedException(val code : String) : Exception(code)
data class OnStopDiscoveryFailedException(val code : String) : Exception(code)

data class OnResolveFailedException(val code : String) : Exception(code)

