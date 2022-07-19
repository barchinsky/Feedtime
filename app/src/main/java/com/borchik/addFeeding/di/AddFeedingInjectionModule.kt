package com.borchik.addFeeding.di

import com.borchik.addFeeding.ui.AddFeedingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AddFeedingInjectionModule {

    val module = module {

        viewModel {
            AddFeedingViewModel(
                dateFormatUtils = get(),
                createFeedingEntityUseCase = get(),
                feedingRepository = get()
            )
        }
    }
}