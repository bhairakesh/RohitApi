package com.rateApi.rohitApi

import com.rateApi.rohitApi.entity.LinkTable
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

object SessionDataPost {

    fun sessionCreate(sessionName: String, code: Int, list: MutableList<LinkTable>): Any? {

        list.forEach { e ->

            val restTemplate = RestTemplate()

            val headers = HttpHeaders()

            headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

            val params: MultiValueMap<String, String> = LinkedMultiValueMap<String, String>()

            params.add("MatchCode", code.toString())
            params.add("SessionName", sessionName)

            val requestEntity: HttpEntity<MultiValueMap<String, String>> =
                HttpEntity<MultiValueMap<String, String>>(params, headers)

            val response: ResponseEntity<String> =
                restTemplate.postForEntity(e.sessionLink, requestEntity, String::class.java)

        }

        return ""

    }

}
