package com.rateApi.rohitApi.backService

import com.rateApi.rohitApi.TempData
import com.rateApi.rohitApi.URLData
import com.rateApi.rohitApi.entity.MatchBhavAutoModifyEntity
import com.rateApi.rohitApi.service.ApiService
import com.rateApi.rohitApi.service.MatchAutoService

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class MatchBackService(val code: Int, private val matchAutoService: MatchAutoService) : Runnable {


    private val okHttpClient =
        OkHttpClient.Builder().readTimeout(1000, TimeUnit.MILLISECONDS).connectTimeout(1000, TimeUnit.MILLISECONDS)
            .build()

    private val builder =
        Retrofit.Builder().baseUrl(URLData.rohitApi).addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build().create(ApiService::class.java)


    override fun run() {

        while (TempData.matchRun.getOrDefault(code, false)) {

            try {
                val resBody = builder.getBookMaker(TempData.gid[code]!!).execute()
                val matchbets = matchAutoService.getForUpdate(code = code, emp = false, type = false)

                val matchModifyList: MutableList<MatchBhavAutoModifyEntity> = ArrayList()

                if (resBody.isSuccessful) {

                    val data = resBody.body()

                    matchbets.forEachIndexed { index, e ->

                        data?.t2?.get(0)?.bm1?.forEachIndexed lit@{ i, row ->
                            val newData = MatchBhavAutoModifyEntity()
                            newData.id = e.id

                            if (e.teamId == row.sid) {
                                if (!e.lockStatus && row.s != "SUSPENDED") {
                                    var back = ((formatForBack(row.b1)) / 100).format(2).toDouble()
                                    var lay = ((formatForLay(row.l1)) / 100).format(2).toDouble()

                                    if (lay in 0.10..0.90) {

                                        val diff = (lay - back).format(2).toDouble()
                                        if (diff == 0.01) {
                                            lay += 0.01
                                        }

                                    }
                                    if (lay <= 0.10 && lay != 0.0) {
                                        val diff1 = (lay - back).format(2).toDouble()
                                        if (diff1 == 0.00) {
                                            lay += 0.01
                                        }
                                    }
                                    if (lay <= 0.02 && lay > 0.0 && back < lay) {
                                        lay = 0.02
                                    }

                                    if (lay <= 0.03 && back < lay) {
                                        back = 0.00
                                    }

                                    newData.kRate = back.format(2).toDouble()
                                    newData.lRate = lay.format(2).toDouble()
                                    newData.dateTime = Date(System.currentTimeMillis())
                                    matchModifyList.add(newData)

                                } else {
                                    newData.kRate = 0.00
                                    newData.lRate = 0.00
                                    matchModifyList.add(newData)
                                }

                                return@lit

                            }

                        }
                    }

                }
                if (TempData.matchCheck.getOrDefault(code, false)) {
                    matchAutoService.updateRate(matchModifyList)
                }
//                Thread.sleep(200)
            } catch (e: Exception) {
                try {
                    Thread.sleep(200)
                } catch (EE: Exception) {
                    println("Exception Match : " + e.message)
                }

            }

        }
    }
    fun Double.format(digits: Int) = "%.${digits}f".format(this)

    fun formatForBack(nm: Double): Double {
        val intVal = nm.toInt()
        return intVal.toDouble()
    }

    fun formatForLay(nm: Double): Double {
        val intVal = nm.toInt()
        val flotValue = (nm - intVal).format(2).toDouble()
        if (flotValue > 0) {
            return if (intVal < 0) {
                intVal.toDouble()
            } else {
                (intVal + 1).toDouble()
            }

        }
        return intVal.toDouble()
    }
}
