package com.tolani.incorporated.repositories

import com.tolani.incorporated.models.Topic
import org.springframework.data.jpa.repository.JpaRepository

interface TopicRepository : JpaRepository<Topic, Long> {
}