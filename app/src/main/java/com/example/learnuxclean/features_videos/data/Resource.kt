package com.example.learnuxclean.features_videos.data

data class Result<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?) = Result(Status.SUCCESS, data, null)

        fun <T> error(message: String, data: T?) = Result(Status.ERROR, data, message)

        fun <T> loading(data: T?) = Result(Status.LOADING, data, null)
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}