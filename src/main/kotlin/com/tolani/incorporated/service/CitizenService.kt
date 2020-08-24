package com.tolani.incorporated.service

import com.tolani.incorporated.dto.CitizenRequest
import com.tolani.incorporated.dto.LoginRequest
import com.tolani.incorporated.models.Citizen
import com.tolani.incorporated.repositories.CitizenRepository
import com.tolani.incorporated.utils.Constants
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CitizenService @Autowired constructor(
        val citizenRepository: CitizenRepository
) {

    val code = "code"
    val message = "message"
    private val logger: Logger = LoggerFactory.getLogger(CommunityService::class.java)

    //Sign in for citizen
    fun createCitizen (citizenRequest: CitizenRequest): ResponseEntity<Any> {
        try {
            val response = HashMap<String, Any>()
            val citizen = Citizen()
            citizen.name = citizenRequest.name
            citizen.email = citizenRequest.email
            citizen.username = citizenRequest.username
            citizen.password = citizenRequest.password
            return when {
                citizenRepository.existsByEmail(citizenRequest.email) -> {
                    response[code] = Constants.CODE99.response
                    response[message] = "Email " + citizenRequest.email + " Exists, You already have an account with us"
                    ResponseEntity(response, HttpStatus.CONFLICT)
                }
                citizenRepository.existsByUsername(citizenRequest.username) -> {
                    response[code] = "98"
                    response[message] = "Username " + citizenRequest.username + " exists, try another"
                    ResponseEntity(response, HttpStatus.CONFLICT)
                }
                else -> {
                    response[code] = Constants.CODE00.response
                    response[message] = Constants.SUCCESS
                    response["citizen"] = citizenRepository.save(citizen)
                    ResponseEntity(response, HttpStatus.CREATED)
                }
            }
        } catch (ex : Exception) {
            return ResponseEntity(logger.error("Exception occurred while creating citizen ${ex.message}"), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    /*
    * Fetches citizen
    */
    fun getCitizen() : ResponseEntity<Any> {
        val response = HashMap<String, Any>()
        response[code] = Constants.CODE00
        response[message] = Constants.SUCCESS
        response["citizens"] = citizenRepository.findAll()
        return ResponseEntity(response, HttpStatus.FOUND)
    }

    /*
        Fetch citizen by Id
     */
    fun getCitizenProfile(id : Long) : ResponseEntity<Any> {
        val response = HashMap<String, Any>()
        return when {
            citizenRepository.existsById(id) -> {
                response[code] = Constants.CODE00
                response[message] = Constants.SUCCESS
                response["citizen_profile"] = citizenRepository.getOne(id)
                ResponseEntity(response, HttpStatus.FOUND)
            } else -> {
                response[code] = Constants.CODE99
                response[message] = "${Constants.FAILED}:" + " Citizen does not exist"
                ResponseEntity(response, HttpStatus.NOT_FOUND)
            }
        }
    }

    fun updateCitizen(id: Long, citizenRequest: CitizenRequest) : ResponseEntity<Any> {
        try {
            val response = HashMap<String, Any>()
            val citizen =  citizenRepository.getOne(id)
            citizen.name = citizenRequest.name
            citizen.email = citizenRequest.email
            citizen.password = citizenRequest.password
            citizen.username = citizenRequest.username
            return when {
                citizenRepository.existsById(id) -> {
                    response[code] = Constants.CODE00.response
                    response[message] = Constants.SUCCESS
                    response["citizen"] = citizenRepository.save(citizen)
                    ResponseEntity(response, HttpStatus.FOUND)
                } else -> {
                    response[code] = Constants.CODE99
                    response[message] = "${Constants.FAILED}:" + " Citizen does not exist"
                    ResponseEntity(response, HttpStatus.NOT_FOUND)
                }
            }
        } catch (ex : Exception) {
            return ResponseEntity(logger.error("Exception occured while updating citizen ${ex.message}"), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

    /*
    * Sign in for citizen
    */
    fun login(loginRequest: LoginRequest) : ResponseEntity<Any> {
        try {
            val response = HashMap<String, Any>()
            return when {
                !citizenRepository.existsByUsername(loginRequest.username) -> {
                    response[code] = Constants.CODE99.response
                    response[message] = "${Constants.FAILED}: "+ "Error while signing in, username is incorrect"
                    ResponseEntity(response, HttpStatus.BAD_REQUEST)
                }
                !citizenRepository.existsByPassword(loginRequest.password) -> {
                    response[code] = Constants.CODE98.response
                    response[message] = "${Constants.FAILED}" + " Error while signing in, password is incorrect"
                    ResponseEntity(response, HttpStatus.BAD_REQUEST)
                }
                else -> {
                    response[code] = "00"
                    response["status"] = Constants.SUCCESS
                    response[message] = "Logged in as " + loginRequest.username.toUpperCase()
                    ResponseEntity(response, HttpStatus.OK)
                }
            }
        } catch (ex : Exception) {
          return ResponseEntity(logger.error("Exception occurred while logging in. Reason: ${ex.message}"), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
