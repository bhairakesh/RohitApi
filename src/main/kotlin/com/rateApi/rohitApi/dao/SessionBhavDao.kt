package com.rateApi.rohitApi.dao

import com.rateApi.rohitApi.entity.SessionBhavEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface SessionBhavDao : JpaRepository<SessionBhavEntity, Int> {

    fun findByUserIdAndCode(userId: Int, code: Int): MutableList<SessionBhavEntity>

    fun findByIdAndUserId(id: Int, userId: Int): SessionBhavEntity?

    fun findByCode(code: Int): MutableList<SessionBhavEntity>

    @Modifying
    @Transactional
    @Query("update SessionBhavEntity s set s.nRate=0.0,s.yRate=0.0,s.nRun=0,s.yRun=0,s.empLockStatus=:lock,s.lockStatus=:lock where s.code=:code")
    fun setEmpLock(lock: Boolean, code: Int): Int?

    @Modifying
    @Transactional
    @Query("update SessionBhavEntity s set s.nRate=0.0,s.yRate=0.0,s.nRun=0,s.yRun=0,s.lockStatus=:lock where s.code=:code")
    fun setLock(lock: Boolean, code: Int): Int?
}