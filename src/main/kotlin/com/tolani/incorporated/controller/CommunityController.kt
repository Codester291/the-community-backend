package com.tolani.incorporated.controller

import com.tolani.incorporated.dto.CommunityRequest
import com.tolani.incorporated.models.Citizen
import com.tolani.incorporated.models.Community
import com.tolani.incorporated.repositories.CommunityRepository
import com.tolani.incorporated.service.CommunityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.HashMap


@RestController
@RequestMapping("/community")
class CommunityController @Autowired constructor(
        val communityService: CommunityService,
        val communityRepository: CommunityRepository
) {

    @GetMapping("/test")
    fun testRelationship() : ResponseEntity<Any> {
        val ayoola = Citizen(1,"Ayoola Balogun", "ayoola@gmail.com", "ceasar_of_lagos", "ceasar",null, null, Date(), Date())
        val drake = Citizen(5,"Aubrey Drake", "drake@ovo.com", "sixGod", "coded", null, null, Date(), Date())
        val community = Community(1, "Lawyers", "Helping the white and black folks", listOf(ayoola, drake))
        val community2 = Community(1, "Vibes Lounge", "To maintain drip as long as we live", listOf(ayoola, drake))
        val response = HashMap<String, Any>()
        response["code"] = "00"
        response["message"] = "SUCCESS YOU JOINED" + community.name
        response["communities"] = communityRepository.saveAll(listOf(community, community2))
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @PostMapping("/create")
    fun createCommunity(@RequestBody @Validated communityRequest: CommunityRequest) : ResponseEntity<Any> {
        return communityService.createCommunity(communityRequest)
    }

    @GetMapping("/")
    fun getCommunity() : ResponseEntity<Any> {
        return communityService.getCommunity()
    }

    @GetMapping("/{id}")
    fun getCommunityProfile(@PathVariable id : Long) : ResponseEntity<Any> {
        return communityService.getCommunityProfile(id)
    }

    @GetMapping("/summary")
    fun fetchCommunitySummary() : ResponseEntity<Any> {
        return communityService.communitySummary()
    }

    @PutMapping("/edit/{id}")
    fun updateCommunity(@PathVariable id: Long, @RequestBody communityRequest: CommunityRequest) : ResponseEntity<Any> {
        return communityService.updateCommunity(id, communityRequest)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteCommunity(@PathVariable id: Long) : ResponseEntity<Any> {
        return communityService.deleteCommunity(id)
    }
}