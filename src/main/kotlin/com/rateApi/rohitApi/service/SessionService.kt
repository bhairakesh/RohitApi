package com.rateApi.rohitApi.service

import com.rateApi.rohitApi.entity.SessionBhavEntity
import jakarta.servlet.http.HttpServletRequest


interface SessionService {


    fun store(sessionBhavEntity: SessionBhavEntity, request: HttpServletRequest): SessionBhavEntity

}