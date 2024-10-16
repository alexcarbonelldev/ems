package com.ems.ems.ui.screen.detail

import com.ems.domain.common.Either
import com.ems.domain.common.error.ErrorType
import com.ems.domain.session.model.HistoricalInfoItem
import com.ems.domain.session.usecase.GetSessionHistoricalInfoUseCase
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
import java.util.Date

class DetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getSessionHistoricalInfoUseCase: GetSessionHistoricalInfoUseCase = mockk()

    private lateinit var viewModel: DetailViewModel

    private fun initViewModel() {
        viewModel = DetailViewModel(
            dispatcherProvider = getDispatcherProvider(mainDispatcherRule.testDispatcher),
            getSessionHistoricalInfoUseCase = getSessionHistoricalInfoUseCase
        )
    }

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun `GIVEN getSessionInfoUseCase returns error WHEN vm is initialized THEN viewState is error`() = runTest {
        coEvery { getSessionHistoricalInfoUseCase() } returns Either.Left(ErrorType.Default)

        initViewModel()

        coVerify(exactly = 1) { getSessionHistoricalInfoUseCase() }
        assertEquals(DetailViewState.Error, viewModel.viewState.value)
    }


    @Test
    fun `GIVEN getSessionInfoUseCase returns success WHEN vm is initialized THEN viewState is data`() = runTest {
        coEvery { getSessionHistoricalInfoUseCase() } returns Either.Right(listOf(historicalInfo))

        initViewModel()

        coVerify(exactly = 1) { getSessionHistoricalInfoUseCase() }
        assertTrue(viewModel.viewState.value is DetailViewState.Data)
    }

    private companion object {

        val historicalInfo = HistoricalInfoItem(
            buildingActivePower = 0.0,
            gridActivePower = 0.0,
            solarPanelActivePower = 0.0,
            quasarsActivePower = 0.0,
            date = Date()
        )
    }
}
