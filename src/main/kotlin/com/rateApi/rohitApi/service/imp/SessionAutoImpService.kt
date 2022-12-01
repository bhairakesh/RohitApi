package com.rateApi.rohitApi.service.imp

import com.rateApi.rohitApi.URLData
import com.rateApi.rohitApi.dao.SessionAutoDao
import com.rateApi.rohitApi.dao.SessionAutoModifyDao
import com.rateApi.rohitApi.entity.SessionAutoModifyEntity
import com.rateApi.rohitApi.entity.SessionBhavAutoEntity
import com.rateApi.rohitApi.jsonData.T3
import com.rateApi.rohitApi.service.ApiService
import com.rateApi.rohitApi.service.SessionAutoService
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Service
class SessionAutoImpService : SessionAutoService {


    @Autowired
    lateinit var sessionBhavAutoDao: SessionAutoDao

    @Autowired
    lateinit var sessionModifyDao: SessionAutoModifyDao


    private final val okHttpClient = OkHttpClient.Builder()
        .readTimeout(500, TimeUnit.MILLISECONDS)
        .connectTimeout(2000, TimeUnit.MILLISECONDS)
        .build()

    private final val builder = Retrofit.Builder().baseUrl(URLData.rohitApi)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(ApiService::class.java)

    override fun getByCode(code: Int): MutableList<SessionBhavAutoEntity> {
        return sessionBhavAutoDao.findByCodeOrderByPosition(code)
    }

    override fun getForUpdate(code: Int, emp: Boolean, type: Boolean): MutableList<SessionBhavAutoEntity> {
        return sessionBhavAutoDao.findByCodeAndType(code, type)
    }

    override fun changeType(status: Boolean, id: Int): Int? {
        return sessionBhavAutoDao.changeType(status, id)
    }

    override fun changeStatus(status: Boolean, id: Int): Int? {
        return sessionBhavAutoDao.changeStatus(status, id)
    }

    override fun allChangeStatus(code: Int, status: Boolean): Int? {
        return sessionBhavAutoDao.allChangeStatus(code, status)
    }

    override fun updateAll(sessionList: MutableList<SessionAutoModifyEntity>): MutableList<SessionAutoModifyEntity> {
        return sessionModifyDao.saveAll(sessionList)
    }

    override fun sessionEmpLockByIds(status: Boolean, ids: List<Int>): Any? {
        return sessionBhavAutoDao.setEmpLockByIds(status, ids)
    }

    override fun getSessions(code: Int, gid: String): Any? {
        val allActive = sessionBhavAutoDao.findByCodeOrderByPosition(code)

        val activeList: MutableList<T3> = ArrayList()

        val removeList: MutableList<T3> = ArrayList()

        val list: MutableList<T3> = mutableListOf()

        try {
            val resEx = builder.getFancyData(gid).execute()

            if (resEx.isSuccessful) {

                val data = resEx.body()

                data?.t3?.forEach { e ->
                    list.add(e)
                }

                allActive.forEach { e ->

                    list.forEachIndexed { iinn, row ->

                        if (e.sid == row.sid) {
                            removeList.add(row)
                            row.lockStatus = e.lockStatus
                            row.id = e.id!!
                            row.type = e.type
                            activeList.add(row)
                            return@forEachIndexed
                        }

                    }
                }

                list.removeAll(removeList)

                return hashMapOf(
                    "list" to list, "active" to activeList
                )

            }

        } catch (e: Exception) {
            println(e)

        }
        return hashMapOf(
            "list" to list, "active" to activeList
        )
    }
}
