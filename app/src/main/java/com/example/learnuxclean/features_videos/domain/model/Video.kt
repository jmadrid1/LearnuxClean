package com.example.learnuxclean.features_videos.domain.model

import java.io.Serializable

data class Video(
    var id: String,
    var title: String,
    var category: String,
    var description: String,
    var thumbnail: String,
    var duration: String) : Serializable {

    constructor() : this(
        "0",
        "",
        "",
        "",
        "0.0F",
        ""
    )

}