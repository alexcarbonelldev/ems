package com.ems.ems.ui.screen.dashboard

import com.ems.domain.common.Either
import com.ems.domain.common.error.ErrorType
import com.ems.domain.session.model.SessionInfo
import com.ems.domain.session.model.SessionLiveInfo
import com.ems.domain.session.model.SessionQuasarInfo
import com.ems.domain.session.model.SessionStatisticsInfo
import com.ems.domain.session.usecase.GetSessionInfoUseCase
import com.ems.ems.utils.MainDispatcherRule
import com.ems.ems.utils.getDispatcherProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DashboardViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getSessionInfoUseCase: GetSessionInfoUseCase = mockk()

    private lateinit var viewModel: DashboardViewModel

    private fun initViewModel() {
        viewModel = DashboardViewModel(
            dispatcherProvider = getDispatcherProvider(mainDispatcherRule.testDispatcher),
            getSessionInfoUseCase = getSessionInfoUseCase
        )
    }

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun `GIVEN getSessionInfoUseCase returns error WHEN vm is initialized THEN viewState is error`() = runTest {
        coEvery { getSessionInfoUseCase() } returns Either.Left(ErrorType.Default)

        initViewModel()

        coVerify(exactly = 1) { getSessionInfoUseCase() }
        assertEquals(DashboardViewState.Error, viewModel.viewState.value)
    }

    @Test
    fun `GIVEN getSessionInfoUseCase returns success WHEN vm is initialized THEN viewState is data`() = runTest {
        coEvery { getSessionInfoUseCase() } returns Either.Right(sessionInfo)

        initViewModel()

        coVerify(exactly = 1) { getSessionInfoUseCase() }
        assertTrue(viewModel.viewState.value is DashboardViewState.Data)
    }

    private companion object {

        val sessionInfo = SessionInfo(
            SessionQuasarInfo(0.0, 0.0),
            SessionLiveInfo(0.0, 0.0, 0.0, 0.0),
            SessionStatisticsInfo(0.0, 0.0, 0.0)
        )
    }
}
