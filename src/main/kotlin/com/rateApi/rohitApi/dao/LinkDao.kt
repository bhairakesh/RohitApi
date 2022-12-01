package com.rateApi.rohitApi.dao

import com.rateApi.rohitApi.entity.LinkTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


interface LinkDao : JpaRepository<LinkTable, Int> {

    @Query("select l from LinkTable l where l.mStatus=:mStatus")
    fun findByMStatus(mStatus: Boolean): MutableList<LinkTable>

    @Query("select l from LinkTable l where l.sStatus=:sStatus")
    fun findBySStatus(sStatus: Boolean): MutableList<LinkTable>
}
