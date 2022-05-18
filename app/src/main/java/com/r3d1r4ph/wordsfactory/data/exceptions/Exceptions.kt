package com.r3d1r4ph.wordsfactory.data.exceptions

import java.io.IOException

class NoConnectivityException : IOException()

class StatusCodeException(override val message: String?) : IOException(message)