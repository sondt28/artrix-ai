package com.son.myapplication.di

import com.son.myapplication.data.repository.StyleRepository
import com.son.myapplication.data.repository.impl.StyleRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<StyleRepository> { StyleRepositoryImpl() }
}