package com.tolani.incorporated.controller

import com.tolani.incorporated.dto.TopicRequest
import com.tolani.incorporated.models.Citizen
import com.tolani.incorporated.models.Topic
import com.tolani.incorporated.repositories.TopicRepository
import com.tolani.incorporated.service.TopicService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.HashMap

@RestController
@RequestMapping("/topic")
class TopicController @Autowired constructor( val topicService: TopicService, val topicRepository: TopicRepository) {

    @GetMapping("/test")
    fun testRelation() : ResponseEntity<Any> {
        val response = HashMap<String, Any>()
        val citizen = Citizen(1,"some", "see", "dwsdes", "sedws", null,null, Date(), Date())
        val topic = Topic(1, "some", "wdeweddxeeced", citizen, Date(), Date())
        response["topic"] = topicRepository.save(topic)
        return ResponseEntity(response, HttpStatus.CREATED)
    }
    @PostMapping("/compose")
    fun createTopic(@RequestBody @Validated topicRequest: TopicRequest) : ResponseEntity<Any> {
        return topicService.createTopic(topicRequest)
    }
}