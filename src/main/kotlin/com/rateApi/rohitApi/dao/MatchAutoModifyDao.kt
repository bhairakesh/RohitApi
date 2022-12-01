package com.rateApi.rohitApi.dao

import com.rateApi.rohitApi.entity.MatchBhavAutoModifyEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MatchAutoModifyDao : JpaRepository<MatchBhavAutoModifyEntity, Int> {


}