package com.tolani.incorporated.repositories

import com.tolani.incorporated.models.Citizen
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CitizenRepository : JpaRepository<Citizen, Long> {
    fun existsByName(name : String) : Boolean
    fun existsByEmail(email : String) : Boolean
    fun existsByUsername(username: String) : Boolean
    fun existsByPassword(password: String) : Boolean
}