package com.tolani.incorporated.dto

import org.springframework.lang.NonNull

class LoginRequest(
        @NonNull
        var username: String,

        @NonNull
        var password: String
)