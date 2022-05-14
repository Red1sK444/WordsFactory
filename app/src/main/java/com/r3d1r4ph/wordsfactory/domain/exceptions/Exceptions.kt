package com.r3d1r4ph.wordsfactory.domain.exceptions

import java.io.IOException

class EmptyFieldException : Exception()

class NoAtSignException : Exception()

class NoAuthorizedException : Exception()

class NoConnectivityException : IOException()

class NotFoundException: IOException()

class NoWordException : Exception()

class StatusCodeException(override val message: String?) : IOException(message)

class UnknownException : Exception()