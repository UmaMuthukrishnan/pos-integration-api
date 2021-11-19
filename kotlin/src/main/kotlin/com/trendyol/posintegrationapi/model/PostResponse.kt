package com.trendyol.posintegrationapi.model

class PostResponse<T> {
    var response: T? = null
        private set

    fun setResponse(response: T) {
        this.response = response
    }
}
