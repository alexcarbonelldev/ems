package com.ems.domain.session.usecase

import com.ems.domain.common.Either
import com.ems.domain.session.repository.SessionRepository
import com.ems.test.MainDispatcherRule
import com.ems.test.getDispatcherProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetSessionHistoricalInfoUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val sessionRepository: SessionRepository = mockk()

    private lateinit var getSessionHistoricalInfoUseCase: GetSessionHistoricalInfoUseCase

    @BeforeEach
    fun setUp() {
        getSessionHistoricalInfoUseCase = GetSessionHistoricalInfoUseCase(
            dispatcherProvider = getDispatcherProvider(mainDispatcherRule.testDispatcher),
            sessionRepository = sessionRepository
        )
    }

    @Test
    fun `GIVEN getHistoricalInfo returns valid response WHEN use case is executed THEN return success response`() =
        runTest {
            coEvery { sessionRepository.getHistoricalInfo() } returns mockk()

            val result = getSessionHistoricalInfoUseCase()

            assertTrue(result is Either.Right)
        }

    @Test
    fun `GIVEN getHistoricalInfo throws exception WHEN use case is executed THEN return failure response`() =
        runTest {
            coEvery { sessionRepository.getHistoricalInfo() } throws Exception()

            val result = getSessionHistoricalInfoUseCase()

            assertTrue(result is Either.Left)
        }
}
