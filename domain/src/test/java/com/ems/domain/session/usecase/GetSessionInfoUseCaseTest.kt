package com.ems.domain.session.usecase

import com.ems.domain.common.Either
import com.ems.domain.session.mapper.SessionInfoMapper
import com.ems.domain.session.repository.SessionRepository
import com.ems.test.MainDispatcherRule
import com.ems.test.getDispatcherProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetSessionInfoUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val sessionRepository: SessionRepository = mockk()
    private val sessionInfoMapper: SessionInfoMapper = mockk()

    private lateinit var getSessionInfoUseCase: GetSessionInfoUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        getSessionInfoUseCase = GetSessionInfoUseCase(
            getDispatcherProvider(mainDispatcherRule.testDispatcher),
            sessionRepository,
            sessionInfoMapper
        )
    }

    @Test
    fun `GIVEN sessionRepository returns valid response WHEN use case is executed THEN return success response`() =
        runTest {
            coEvery { sessionRepository.getLiveInfo() } returns mockk()
            coEvery { sessionRepository.getHistoricalInfo() } returns mockk()
            coEvery { sessionInfoMapper.map(any(), any()) } returns mockk()

            val result = getSessionInfoUseCase()

            assertTrue(result is Either.Right)
        }

    @Test
    fun `GIVEN getLiveInfo throws exception WHEN use case is executed THEN return failure response`() =
        runTest {
            coEvery { sessionRepository.getLiveInfo() } throws Exception()
            coEvery { sessionRepository.getHistoricalInfo() } returns mockk()
            coEvery { sessionInfoMapper.map(any(), any()) } returns mockk()

            val result = getSessionInfoUseCase()

            assertTrue(result is Either.Left)
        }

    @Test
    fun `GIVEN getHistoricalInfo throws exception WHEN use case is executed THEN return failure response`() =
        runTest {
            coEvery { sessionRepository.getLiveInfo() } returns mockk()
            coEvery { sessionRepository.getHistoricalInfo() } throws Exception()
            coEvery { sessionInfoMapper.map(any(), any()) } returns mockk()

            val result = getSessionInfoUseCase()

            assertTrue(result is Either.Left)
        }
}
