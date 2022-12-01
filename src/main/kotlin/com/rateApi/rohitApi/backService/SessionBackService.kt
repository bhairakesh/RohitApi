package com.rateApi.rohitApi.backService


import com.rateApi.rohitApi.TempData
import com.rateApi.rohitApi.URLData
import com.rateApi.rohitApi.entity.SessionAutoModifyEntity
import com.rateApi.rohitApi.service.ApiService
import com.rateApi.rohitApi.service.SessionAutoService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class SessionBackService(val code: Int, private val sessionService: SessionAutoService) : Runnable {


    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(1000, TimeUnit.MILLISECONDS)
        .connectTimeout(1000, TimeUnit.MILLISECONDS)
        .build()

    private val builder =
        Retrofit.Builder().baseUrl(URLData.rohitApi).addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)

    override fun run() {

        while (TempData.sessionRun.getOrDefault(code, false)) {

            try {

                val sessions = sessionService.getForUpdate(code, type = false, emp = false)

                val responseBody = builder.getFancyData(TempData.gid[code]!!).execute()

                if (responseBody.isSuccessful) {
                    val data = responseBody.body()
                    val newSess: MutableList<SessionAutoModifyEntity> = ArrayList()

                    sessions.forEachIndexed { index, e ->
                        data?.t3?.forEach lit@{ row ->
                            if (e.sid == row.sid) {
                                val newData = SessionAutoModifyEntity()
                                newData.id = e.id!!
                                if (!e.lockStatus && row.gstatus == "") {
                                    newData.nRun = row.l1.toInt()
                                    newData.nRate = (row.ls1 / 100)
                                    newData.yRun = row.b1.toInt()
                                    newData.yRate = (row.bs1 / 100)
                                    newData.dateTime = Date(System.currentTimeMillis())
                                    newSess.add(newData)
                                } else {
                                    newData.nRun = 0
                                    newData.nRate = 0.00
                                    newData.yRun = 0
                                    newData.yRate = 0.00
                                    newSess.add(newData)
                                }

                                return@lit

                            }

                        }

                    }


                    if (TempData.sessionCheck.getOrDefault(code, false)) {
                        val unSelectList: MutableList<Int> = ArrayList()
                        val selectList: MutableList<Int> = ArrayList()
                        sessions.forEach { e ->
                            var check = true
                            newSess.forEach check@{
                                if (it.id == e.id) {
                                    check = false
                                    return@check
                                }
                            }
                            if (check) {
                                unSelectList.add(e.id!!)
                            }

                            if (!check && e.empLockStatus) {
                                selectList.add(e.id!!)
                            }
                        }

                        if (newSess.isNotEmpty()) {
                            sessionService.updateAll(newSess)
                        }
                        if (unSelectList.isNotEmpty()) {
                            sessionService.sessionEmpLockByIds(true, unSelectList)
                        }
                        if (selectList.isNotEmpty()) {
                            sessionService.sessionEmpLockByIds(false, selectList)
                        }
                    }
                }
//                Thread.sleep(200)
            } catch (e: Exception) {
                try {
                    Thread.sleep(200)
                } catch (EE: Exception) {
                    println("Exception Session : " + e.message)
                }
            }

        }

    }


}
