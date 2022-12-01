package com.rateApi.rohitApi.service.imp


import com.rateApi.rohitApi.URLData
import com.rateApi.rohitApi.dao.MatchAutoDao
import com.rateApi.rohitApi.dao.MatchAutoModifyDao
import com.rateApi.rohitApi.entity.MatchBhavAutoEntity
import com.rateApi.rohitApi.entity.MatchBhavAutoModifyEntity
import com.rateApi.rohitApi.jsonData.Bm1
import com.rateApi.rohitApi.service.ApiService
import com.rateApi.rohitApi.service.MatchAutoService

import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Service
class MatchAutoImpService : MatchAutoService {

    @Autowired
    lateinit var matchAutoDao: MatchAutoDao

    @Autowired
    lateinit var matchAutoModifyDao: MatchAutoModifyDao

    private final val okHttpClient =
        OkHttpClient.Builder().readTimeout(800, TimeUnit.MILLISECONDS).connectTimeout(800, TimeUnit.MILLISECONDS)
            .build()

    val builder = Retrofit.Builder().baseUrl(URLData.rohitApi).addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient).build().create(ApiService::class.java)


    override fun getByCode(code: Int): MutableList<MatchBhavAutoEntity> {
        return matchAutoDao.findByCodeOrderById(code)
    }

    override fun getForUpdate(code: Int, emp: Boolean, type: Boolean): MutableList<MatchBhavAutoEntity> {
        return matchAutoDao.findByCodeAndEmpLockStatusAndType(code, emp, type)
    }

    override fun updateRate(matchList: MutableList<MatchBhavAutoModifyEntity>): MutableList<MatchBhavAutoModifyEntity> {
        return matchAutoModifyDao.saveAll(matchList)
    }

    override fun updateType(id: Int, type: Boolean): Int? {

        return matchAutoDao.updateType(id, type)
    }

    override fun allLockUnLock(status: Boolean, code: Int): Int? {
        return matchAutoDao.allLockUnLock(status, code)
    }

    override fun getMatchData(code: Int, gid: String): Any? {
        val map: HashMap<String, Any?> = HashMap()
        val newData: MutableList<Bm1> = ArrayList()
        val matchBets = matchAutoDao.findByCodeOrderById(code)
        try {
            val responseBody = builder.getBookMaker(gid).execute()

            if (responseBody.isSuccessful) {

                val data = responseBody.body()

                matchBets.forEach { e ->
                    data?.t2?.get(0)?.bm1?.forEachIndexed lit@{ i, row ->
                        if (row.sid == e.teamId) {
                            var back = (formatForBack(row.b1)) / 100
                            var lay = (formatForLay(row.l1)) / 100

                            if (lay <= 0.90 && lay >= 0.10) {

                                val diff = (lay - back).format(2).toDouble()

                                if (diff == 0.01) {
                                    lay = lay + 0.01
                                }

                            }
                            if (lay <= 0.10 && lay != 0.0) {
                                val diff1 = (lay - back).format(2).toDouble()

                                if (diff1 == 0.00) {
                                    lay = lay + 0.01
                                }
                            }
                            if (lay <= 0.02 && lay > 0.0 && back < lay) {
                                lay = 0.02
                            }

                            if (lay <= 0.03 && back < lay) {
                                back = 0.00
                            }
                            row.b1 = back.format(2).toDouble()
                            row.l1 = lay.format(2).toDouble()
                            row.type = e.type
                            row.team = e.team
                            row.id = e.id
                            row.lock = e.lockStatus
                            newData.add(row)
                            return@lit
                        }
                    }
                }
            }

            if (newData.isEmpty())
                matchBets.forEach {
                    val row = Bm1()
                    row.type = it.type
                    row.team = it.team
                    row.id = it.id
                    row.lock = it.lockStatus
                    newData.add(row)
                }

            map["di"] = newData
        } catch (e: Exception) {
//            e.printStackTrace()
            matchBets.forEach {
                val row = Bm1()
                row.type = it.type
                row.team = it.team
                row.id = it.id
                row.lock = it.lockStatus
                newData.add(row)
            }

            map["di"] = newData

        }

        return map
    }

    private final fun Double.format(digits: Int) = "%.${digits}f".format(this)

    private final fun formatForBack(nm: Double): Double {
        val intVal = nm.toInt()
        return intVal.toDouble()
    }

    private final fun formatForLay(nm: Double): Double {
        val intVal = nm.toInt()
        val flotValue = (nm - intVal).format(2).toDouble()
        if (flotValue > 0) {
            return if (intVal < 10) {
                intVal.toDouble()
            } else {
                (intVal + 1).toDouble()
            }

        }
        return intVal.toDouble()
    }

}


