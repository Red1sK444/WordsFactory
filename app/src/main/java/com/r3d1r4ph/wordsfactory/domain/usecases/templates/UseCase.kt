package com.r3d1r4ph.wordsfactory.domain.usecases.templates

interface UseCase<Input: Any, Output> {
    suspend fun execute(input: Input): Output
}