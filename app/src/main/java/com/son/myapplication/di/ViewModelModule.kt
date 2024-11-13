package com.son.myapplication.di

import com.son.myapplication.ui.screen.MainViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { MainViewModel(get()) }
}