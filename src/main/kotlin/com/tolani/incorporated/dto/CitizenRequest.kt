package com.tolani.incorporated.dto

import org.springframework.lang.NonNull

class CitizenRequest (
        @NonNull
        val name: String,
        @NonNull
        val email: String,
        @NonNull
        val username: String,
        @NonNull
        val password: String
)