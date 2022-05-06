package com.r3d1r4ph.wordsfactory.common.exceptions

import java.io.IOException

class StatusCodeException(override val message: String?) : IOException(message)