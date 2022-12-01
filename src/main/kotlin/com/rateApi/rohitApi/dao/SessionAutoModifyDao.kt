package com.rateApi.rohitApi.dao

import com.rateApi.rohitApi.entity.SessionAutoModifyEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SessionAutoModifyDao : JpaRepository<SessionAutoModifyEntity, Int> {

}