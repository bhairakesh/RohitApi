package com.rateApi.rohitApi.service.imp

import com.rateApi.rohitApi.dao.SessionBhavDao
import com.rateApi.rohitApi.entity.SessionBhavEntity
import com.rateApi.rohitApi.service.SessionService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SessionServiceImp : SessionService {

    @Autowired
    lateinit var sessionBhavDao: SessionBhavDao

    override fun store(sessionBhavEntity: SessionBhavEntity, request: HttpServletRequest): SessionBhavEntity {
        sessionBhavEntity.name = sessionBhavEntity.name.trim()
        return sessionBhavDao.save(sessionBhavEntity)
    }

}