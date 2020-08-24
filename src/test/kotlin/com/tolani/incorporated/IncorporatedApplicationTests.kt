package com.tolani.incorporated

import com.tolani.incorporated.controller.CitizenController
import com.tolani.incorporated.dto.CitizenRequest
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@SpringBootTest
class IncorporatedApplicationTests {

    //	private lateinit var citizenService: CitizenService
    @Test
    fun contextLoads() {
    }


    @Test
    fun citizenCreationTest() {
        val mocked = mock(CitizenController::class.java)
        var citizenRequest = CitizenRequest("", "", "", "")

        Mockito.`when`(
                mocked.citizenService
                        .getCitizen())
                .thenReturn(ResponseEntity(
                        Response("00", "success", citizenRequest), HttpStatus.CREATED))
    }
}

