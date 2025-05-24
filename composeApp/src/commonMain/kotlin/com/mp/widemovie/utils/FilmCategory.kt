package com.mp.widemovie.utils

import com.daemonz.base_sdk.model.ListData


fun getFilmCategories(data: ListData?): List<String> {
    return data?.data?.items?.filter { true }?.map { it.category.firstOrNull()?.name.toString() }
        ?: emptyList()
}