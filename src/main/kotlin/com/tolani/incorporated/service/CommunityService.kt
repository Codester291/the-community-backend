package com.tolani.incorporated.service

import com.tolani.incorporated.dto.CommunityRequest
import com.tolani.incorporated.models.Community
import com.tolani.incorporated.repositories.CommunityRepository
import com.tolani.incorporated.utils.Constants
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CommunityService @Autowired constructor(
        private val communityRepository: CommunityRepository
) {

    val code = "code"
    val message = "message"

    private val logger: Logger = LoggerFactory.getLogger(CommunityService::class.java)

    fun createCommunity(communityRequest: CommunityRequest): ResponseEntity<Any> {
        try {
            val response = HashMap<String, Any>()
            val community = Community()
            community.name = communityRequest.name
            community.cause = communityRequest.cause
            return when {
                communityRepository.existsByName(communityRequest.name) -> {
                    response[code] = "99"
                    response[message] = communityRequest.name + " already exists"
                    ResponseEntity(response, HttpStatus.CONFLICT)
                }
                else -> {
                    response[code] = "00"
                    response[message] = "SUCCESS"
                    response["community"] = communityRepository.save(community)
                    ResponseEntity(response, HttpStatus.CREATED)
                }
            }
        } catch (ex: Exception) {
            return ResponseEntity(logger.error("Exception occurred while updating community. Reason: ${ex.message}"), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

    fun getCommunity(): ResponseEntity<Any> {
        val response = HashMap<String, Any>()
        response[code] = "00"
        response[message] = "SUCCESS"
        response["communities"] = communityRepository.findAll()
        return ResponseEntity(response, HttpStatus.OK)
    }

    fun getCommunityProfile(id: Long) : ResponseEntity<Any> {
        val response = HashMap<String, Any>()
        return when {
            communityRepository.existsById(id) -> {
                response[code] = "00"
                response[message] = "SUCCESS"
                response["community_profile"] = communityRepository.getOne(id)
                ResponseEntity(response, HttpStatus.FOUND)
            } else -> {
                response[code] = "99"
                response[message] = "community does not exist"
                ResponseEntity(response, HttpStatus.NOT_FOUND)
            }
        }
    }

    fun updateCommunity(id: Long, communityRequest: CommunityRequest) : ResponseEntity<Any> {
        try {
            val response = HashMap<String, Any>()
            val community = communityRepository.getOne(id)
            community.name = communityRequest.name
            community.cause = communityRequest.cause
            return when {
                communityRepository.existsById(id) -> {
                    response[code] = Constants.CODE00.response
                    response[message] = Constants.SUCCESS
                    response["community"] = communityRepository.save(community)
                    ResponseEntity(response, HttpStatus.ACCEPTED)
                } else -> {
                    response[code] = Constants.CODE99.response
                    response[message] = "${Constants.FAILED}:" + " community does not exist"
                    ResponseEntity(response, HttpStatus.NOT_FOUND)
                }
            }
        }catch (ex: Exception) {
            return ResponseEntity(logger.error("Exception occurred while updating community. Reason: ${ex.message}"), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

    fun communitySummary() : ResponseEntity<Any> {
        val communitySummary = communityRepository.communitySummary()
        val count = communityRepository.count()
        val response = HashMap<String, Any>()
        response[code] = Constants.CODE00.response
        response[message] = Constants.SUCCESS
        response["summary"] = communitySummary
        response["number"] = count
        return ResponseEntity(response, HttpStatus.ACCEPTED)
    }

    fun deleteCommunity(id: Long): ResponseEntity<Any> {
        val response = HashMap<String, Any>()
        return when {
            communityRepository.existsById(id) -> {
                val community = communityRepository.getOne(id)
                communityRepository.delete(community)
                response["code"] = "00"
                response["message"] = community.name + " has been deleted"
                ResponseEntity(response, HttpStatus.OK)
            }
            else -> {
                response[code] = "99"
                response[message] = "Community does not exist"
                ResponseEntity(response, HttpStatus.NOT_FOUND)
            }
        }
    }
}