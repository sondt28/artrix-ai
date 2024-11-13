package com.son.myapplication.di

val appModule = buildList {
    add(networkModule)
    add(repositoryModule)
    add(viewModelModule)
}