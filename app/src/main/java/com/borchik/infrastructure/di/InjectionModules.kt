package com.borchik.infrastructure.di

import com.borchik.addFeeding.di.AddFeedingInjectionModule
import com.borchik.dashboard.di.DashboardInjectionModule

object InjectionModules {

    val modules = listOf(
        AddFeedingInjectionModule.module,
        InfrastructureInjectionModule.module,
        DashboardInjectionModule.module
    )
}