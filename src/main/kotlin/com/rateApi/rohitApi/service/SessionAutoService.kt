package com.rateApi.rohitApi.service

import com.rateApi.rohitApi.entity.SessionAutoModifyEntity
import com.rateApi.rohitApi.entity.SessionBhavAutoEntity


interface SessionAutoService {

    fun getByCode(code: Int): MutableList<SessionBhavAutoEntity>

    fun getForUpdate(code: Int, emp: Boolean, type: Boolean): MutableList<SessionBhavAutoEntity>

    fun changeType(status: Boolean, id: Int): Int?

    fun changeStatus(status: Boolean, id: Int): Int?

    fun allChangeStatus(code: Int, status: Boolean): Int?

    fun updateAll(sessionList: MutableList<SessionAutoModifyEntity>): MutableList<SessionAutoModifyEntity>

    fun sessionEmpLockByIds(status: Boolean, ids: List<Int>): Any?

    fun getSessions(code: Int, gid: String): Any?

}
