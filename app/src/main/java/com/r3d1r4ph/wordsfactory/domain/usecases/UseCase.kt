package com.r3d1r4ph.wordsfactory.domain.usecases

interface UseCase<Input: Any, Output> {
    suspend operator fun invoke(input: Input): Output
}