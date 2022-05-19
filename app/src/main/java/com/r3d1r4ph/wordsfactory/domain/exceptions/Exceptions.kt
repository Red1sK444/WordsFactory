package com.r3d1r4ph.wordsfactory.domain.exceptions

import java.io.IOException

class EmptyFieldException : Exception()

class NoAtSignException : Exception()

class NoAuthorizedException : Exception()

class NotFoundException: IOException()

class NoWordException : Exception()

class WordNotSavedException : Exception()

class UnknownException : Exception()