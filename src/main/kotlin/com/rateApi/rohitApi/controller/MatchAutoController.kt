package com.rateApi.rohitApi.controller


import com.rateApi.rohitApi.ServiceData
import com.rateApi.rohitApi.TempData
import com.rateApi.rohitApi.backService.MatchBackService
import com.rateApi.rohitApi.entity.MatchBhavEntity
import com.rateApi.rohitApi.service.MatchAutoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/auto/v1/match")
class MatchAutoController {

    @Autowired
    lateinit var matchAutoService: MatchAutoService

    @PostMapping("/changeType")
    fun changeType(@RequestBody m: MatchBhavEntity): Any? {
        return matchAutoService.updateType(m.id!!, m.type)
    }

    @GetMapping("/get/{code}/{gid}")
    fun get(@PathVariable("code") code: Int, @PathVariable gid: String): Any? {
        return matchAutoService.getMatchData(code, gid)
    }

    @PostMapping("/changeAllCrick")
    fun changeAllStatus(@RequestBody m: MatchBhavEntity): Any? {
        matchAutoService.allLockUnLock(m.lockStatus, m.code)
        val msg: String = if (m.lockStatus) {
            stopService(m.code)
        } else {
            startService(m.code)
        }
        return hashMapOf(
            "msg" to msg
        )
    }


    private fun startService(code: Int): String {

        ServiceData.matchServiceList.forEach { e ->
            if (e.code == code) {
                return "Service Already Start"
            }
        }
        val matchService = MatchBackService(code, matchAutoService)
        TempData.matchRun[code] = true
        TempData.matchCheck[code] = true
        val thread = Thread(matchService)
        ServiceData.matchServiceList.add(matchService)
        thread.start()
        return "service Start $code"

    }

    private fun stopService(code: Int): String {

        var inn = -1
        ServiceData.matchServiceList.forEachIndexed { index, e ->
            if (e.code == code) {
                TempData.matchRun[code] = false
                TempData.matchCheck[code] = false
                inn = index
            }
        }
        if (inn >= 0) ServiceData.matchServiceList.removeAt(inn)


        return "stop service at $code"
    }

}