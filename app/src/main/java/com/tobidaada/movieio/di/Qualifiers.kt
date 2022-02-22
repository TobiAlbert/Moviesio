package com.tobidaada.movieio.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IOCoroutineDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainCoroutineDispatcher