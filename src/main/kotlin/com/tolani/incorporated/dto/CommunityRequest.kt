package com.tolani.incorporated.dto

import org.springframework.lang.NonNull

data class CommunityRequest (
        @NonNull
        var name: String,
        @NonNull
        var cause: String
)