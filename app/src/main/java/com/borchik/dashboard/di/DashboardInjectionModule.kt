package com.borchik.dashboard.di

import com.borchik.dashboard.ui.DashboardViewModel
import com.borchik.dashboard.usecase.CreateFeedingEntityUseCase
import com.borchik.dashboard.usecase.FormatFeedingIntervalUseCase
import com.borchik.dashboard.usecase.GetFeedingsUseCase
import com.borchik.dashboard.usecase.GetTimeBetweenFeedingsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DashboardInjectionModule {

    val module = module {

        viewModel {
            DashboardViewModel(
                feedingRepository = get(),
                createFeedingEntityUseCase = get(),
                getFeedingsUseCase = get(),
                formatFeedingIntervalUseCase = get(),
                getTimeBetweenFeedingsUseCase = get()
            )
        }

        factory {
            CreateFeedingEntityUseCase()
        }

        factory {
            FormatFeedingIntervalUseCase()
        }

        factory {
            GetFeedingsUseCase(
                formatFeedingIntervalUseCase = get(),
                feedingRepository = get()
            )
        }

        factory {
            GetTimeBetweenFeedingsUseCase(
                feedingRepository = get(),
                formatFeedingIntervalUseCase = get()
            )
        }
    }
}