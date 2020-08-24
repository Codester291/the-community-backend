package com.tolani.incorporated.service

import com.tolani.incorporated.dto.TopicRequest
import com.tolani.incorporated.models.Topic
import com.tolani.incorporated.repositories.TopicRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class TopicService @Autowired constructor( val topicRepository: TopicRepository) {

    private val code = "code"
    private val message = "message"

    fun createTopic(topicRequest: TopicRequest) : ResponseEntity<Any> {
        val response = HashMap<String, Any>()
        val topic = Topic()
        topic.title = topicRequest.title
        topic.content = topicRequest.content
        response[code] = "00"
        response[message] = "SUCCESS"
        response["topic"] = topicRepository.save(topic)
        return ResponseEntity(response, HttpStatus.CREATED)
    }
}