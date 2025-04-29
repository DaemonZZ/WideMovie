package com.app.movie.sharelibrary.datasource.api

import com.app.movie.sharelibrary.model.Item

interface ApiRequestCallBack {
    fun onSuccess(listItem: List<Item>) {}
    fun onSuccess(item: Item) {}
    fun onFailure(err: String)
}