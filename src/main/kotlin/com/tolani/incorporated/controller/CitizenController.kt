package com.tolani.incorporated.controller

import com.tolani.incorporated.dto.CitizenRequest
import com.tolani.incorporated.dto.LoginRequest
import com.tolani.incorporated.models.Citizen
import com.tolani.incorporated.models.Community
import com.tolani.incorporated.repositories.CitizenRepository
import com.tolani.incorporated.service.CitizenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.HashMap

@RestController
@RequestMapping("/citizen")
class CitizenController @Autowired constructor(
        val citizenService: CitizenService,
        val citizenRepository: CitizenRepository
) {

    @GetMapping("/test")
    fun testRel() : ResponseEntity<Any> {
        val activist = Community(4, "Activist", "To uphold Social Justice")
        val tani = Citizen(1, "Tolani Olatunde", "t@_.com", "fluffy_gator", "tolani",null, listOf(activist), Date(), Date())
        val citizen = citizenRepository.save(tani)
        val response = HashMap<String, Any>()
        response["code"] = "00"
        response["citizen"] = citizen
        return ResponseEntity(response, HttpStatus.CREATED)
    }
    @GetMapping("/blog")
    fun hello(model: Model) : String {
        model["title"] = "Tolani"
        return "Tolani"
    }

    @PostMapping("/create")
    fun createCitizen(@RequestBody @Validated citizenRequest: CitizenRequest) : ResponseEntity<Any> {
        return citizenService.createCitizen(citizenRequest)
    }

    @GetMapping("/")
    fun getCitizens() : ResponseEntity<Any> {
        return citizenService.getCitizen()
    }

    @GetMapping("/{id}")
    fun getCitizenProfile(@PathVariable id: Long) : ResponseEntity<Any> {
        return citizenService.getCitizenProfile(id)
    }

    @PutMapping("update/{id}")
    fun updateCitizen(@PathVariable id: Long, @RequestBody citizenRequest: CitizenRequest) : ResponseEntity<Any> {
        return citizenService.updateCitizen(id, citizenRequest)
    }

    @PostMapping("/login")
    fun loginCitizen(@RequestBody @Validated loginRequest: LoginRequest) : ResponseEntity<Any> {
        return citizenService.login(loginRequest)
    }

}