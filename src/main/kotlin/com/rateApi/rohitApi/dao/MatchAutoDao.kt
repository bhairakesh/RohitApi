package com.rateApi.rohitApi.dao

import com.rateApi.rohitApi.entity.MatchBhavAutoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface MatchAutoDao : JpaRepository<MatchBhavAutoEntity, Int> {

    fun findByCodeAndEmpLockStatusAndType(code: Int, emp: Boolean, type: Boolean): MutableList<MatchBhavAutoEntity>

    fun findByCodeOrderById(code: Int): MutableList<MatchBhavAutoEntity>


    @Transactional
    @Modifying
    @Query("update MatchBhavAutoEntity m set m.type=:type where m.id=:id")
    fun updateType(@Param("id") id: Int, @Param("type") type: Boolean): Int?


    @Transactional
    @Modifying
    @Query("update MatchBhavAutoEntity m set m.lockStatus=:status,m.lRate=0.00,m.kRate=0.00 where m.code=:code")
    fun allLockUnLock(@Param("status") status: Boolean, @Param("code") code: Int): Int


}