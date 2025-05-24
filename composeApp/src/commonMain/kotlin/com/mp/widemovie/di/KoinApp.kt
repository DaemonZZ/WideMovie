package com.mp.widemovie.di

import com.daemonz.base_sdk.base.BaseRepository
import com.daemonz.base_sdk.repo.AppRepository
import com.mp.widemovie.getPlatform
import com.mp.widemovie.utils.provideIoCoroutineScope
import com.mp.widemovie.viewmodel.DetailViewModel
import com.mp.widemovie.viewmodel.HomeViewModel
import com.mp.widemovie.viewmodel.MoviePlayerViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.koin.dsl.module


fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        printLogger()
        includes(config)
        modules(appModule)
    }
}

val appModule = module {
    single<CoroutineScope> { provideIoCoroutineScope() }
    singleOf(::AppRepository) { bind<BaseRepository>() }
    viewModel { HomeViewModel(get(),get()) }
    viewModel { DetailViewModel(get(),get()) }
    viewModel { MoviePlayerViewModel(get()) }
    factory { getPlatform(this) }
}