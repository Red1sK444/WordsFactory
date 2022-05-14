package com.r3d1r4ph.wordsfactory.domain.usecases.templates

interface UseCaseWithoutParam<out Output> {
    suspend fun execute(): Output
}