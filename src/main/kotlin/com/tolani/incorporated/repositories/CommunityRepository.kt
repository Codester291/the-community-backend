package com.tolani.incorporated.repositories

import com.tolani.incorporated.models.Community
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CommunityRepository : JpaRepository<Community, Long> {
    fun existsByName(name: String) : Boolean

    @Query(nativeQuery = true, value = "select community_id as communityId, count(citizen_id) as numberOfUsers from citizen_community")
    fun communitySummary()
}