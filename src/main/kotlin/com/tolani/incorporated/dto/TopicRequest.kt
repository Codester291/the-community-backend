package com.tolani.incorporated.dto

import org.springframework.lang.NonNull

data class TopicRequest (
        @NonNull
        var title: String,
        @NonNull
        var content: String
)