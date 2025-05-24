package com.mp.widemovie.utils


import com.daemonz.base_sdk.model.home.HomeResponse


fun getFilmCategories(data: HomeResponse?): List<String> {
    return data?.data?.items?.filter { true }?.map { it.category?.firstOrNull()?.name.toString() }
        ?: emptyList()
}