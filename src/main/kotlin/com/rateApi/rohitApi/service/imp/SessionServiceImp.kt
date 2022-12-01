package com.rateApi.rohitApi.service.imp

import com.rateApi.rohitApi.SessionDataPost
import com.rateApi.rohitApi.dao.LinkDao
import com.rateApi.rohitApi.dao.SessionBhavDao
import com.rateApi.rohitApi.entity.SessionBhavEntity
import com.rateApi.rohitApi.service.SessionService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.regex.Pattern

@Service
class SessionServiceImp : SessionService {

    @Autowired
    lateinit var sessionBhavDao: SessionBhavDao

    @Autowired
    lateinit var linkTableRepo: LinkDao

    override fun store(sessionBhavEntity: SessionBhavEntity, request: HttpServletRequest): SessionBhavEntity {
        sessionBhavEntity.name = sessionBhavEntity.name.trim()

        sessionBhavEntity.name = sessionBhavEntity.name.trim().uppercase()

        val rc = Pattern.compile("(^[A-Za-z0-9 .()]+\$)")

        val matcher = rc.matcher(sessionBhavEntity.name).matches()

        if (!matcher) {
            sessionBhavEntity.sid = "Error"
            return sessionBhavEntity
        }

        val sessAlready = sessionBhavDao.findByCodeAndName(sessionBhavEntity.code, sessionBhavEntity.name)
        if (sessAlready != null) {
            sessAlready.sid = "Error"
            return sessAlready
        }

        val allSession = sessionBhavDao.findByCodeOrderByPositionAsc(sessionBhavEntity.code)
        val newSessList: MutableList<SessionBhavEntity> = mutableListOf()
        allSession.forEach {
            if (it.position >= sessionBhavEntity.position) {
                it.position += 1
                newSessList.add(it)
            }

        }

        val list = linkTableRepo.findBySStatus(true)
        SessionDataPost.sessionCreate(sessionBhavEntity.name, sessionBhavEntity.code, list)
        sessionBhavDao.saveAll(newSessList)
        return sessionBhavDao.save(sessionBhavEntity)
    }

}
