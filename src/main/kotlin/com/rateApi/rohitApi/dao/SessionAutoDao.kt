package com.rateApi.rohitApi.dao


import com.rateApi.rohitApi.entity.SessionBhavAutoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional


interface SessionAutoDao : JpaRepository<SessionBhavAutoEntity, Int> {


    fun findByCodeAndEmpLockStatusAndType(code: Int, emp: Boolean, type: Boolean): MutableList<SessionBhavAutoEntity>


    fun findByCodeOrderByPosition(code: Int): MutableList<SessionBhavAutoEntity>

    @Transactional
    @Modifying
    @Query("update SessionBhavAutoEntity s set s.type=:status,s.nRun=0,s.yRun=0,s.nRate=0,s.yRate=0 where s.id=:id")
    fun changeType(status: Boolean, id: Int): Int?

    @Transactional
    @Modifying
    @Query("update SessionBhavAutoEntity s set s.lockStatus=:status,s.nRun=0,s.yRun=0,s.nRate=0,s.yRate=0 where s.id=:id")
    fun changeStatus(status: Boolean, id: Int): Int?

    @Transactional
    @Modifying
    @Query("update SessionBhavAutoEntity s set s.lockStatus=:status,s.nRun=0,s.yRun=0,s.nRate=0,s.yRate=0 where s.code=:code")
    fun allChangeStatus(code: Int, status: Boolean): Int?

    @Modifying
    @Transactional
    @Query("update SessionBhavEntity  s set s.nRate=0.0,s.yRate=0.0,s.nRun=0,s.yRun=0 ,s.empLockStatus=:status  where s.id in (:ids)")
    fun setEmpLockByIds(status: Boolean, ids: List<Int>): Int?
}
