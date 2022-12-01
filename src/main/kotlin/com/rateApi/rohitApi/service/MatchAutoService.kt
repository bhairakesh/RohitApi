package com.rateApi.rohitApi.service

import com.rateApi.rohitApi.entity.MatchBhavAutoEntity
import com.rateApi.rohitApi.entity.MatchBhavAutoModifyEntity


interface MatchAutoService {

    fun getByCode(code: Int): MutableList<MatchBhavAutoEntity>

    fun getForUpdate(code: Int, emp: Boolean, type: Boolean): MutableList<MatchBhavAutoEntity>

    fun updateRate(matchList: MutableList<MatchBhavAutoModifyEntity>): MutableList<MatchBhavAutoModifyEntity>

    fun updateType(id: Int, type: Boolean): Int?

    fun allLockUnLock(status: Boolean, code: Int): Int?

    fun getMatchData(code: Int, gid: String): Any?

}