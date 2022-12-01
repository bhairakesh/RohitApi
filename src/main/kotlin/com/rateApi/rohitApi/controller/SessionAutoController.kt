package com.rateApi.rohitApi.controller

import com.rateApi.rohitApi.ServiceData
import com.rateApi.rohitApi.TempData
import com.rateApi.rohitApi.backService.SessionBackService
import com.rateApi.rohitApi.entity.SessionBhavEntity
import com.rateApi.rohitApi.service.SessionAutoService
import com.rateApi.rohitApi.service.SessionService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auto/v1/session")
@CrossOrigin
class SessionAutoController {

    @Autowired
    lateinit var sessionAutoService: SessionAutoService

    @Autowired
    lateinit var sessionService: SessionService


    @PostMapping("/create")
    @ResponseBody
    fun createSessionByApi(@ModelAttribute sessionBhavEntity: SessionBhavEntity, request: HttpServletRequest): Any? {
        return sessionService.store(sessionBhavEntity, request)
    }


    @GetMapping("/get/{code}/{gid}")
    fun getSession(@PathVariable code: Int, @PathVariable gid: String): Any? {
        return sessionAutoService.getSessions(code, gid)
    }

    @PostMapping("/changeType")
    fun changeType(@RequestBody body: SessionBhavEntity): Any? {
        return sessionAutoService.changeType(body.type, body.id!!)
    }


    @Transactional
    @PostMapping("/changeStatus")
    fun changeStatus(@RequestBody s: SessionBhavEntity): Any? {
        return sessionAutoService.changeStatus(s.lockStatus, s.id!!)
    }


    @Transactional
    @PostMapping("/changeAll")
    fun changeAllStatus(@RequestBody s: SessionBhavEntity): Any {
        sessionAutoService.allChangeStatus(s.code, s.lockStatus)
        return if (s.lockStatus) {
            hashMapOf(
                "msg" to stopService(s.code)
            )
        } else {
            hashMapOf(
                "msg" to startService(s.code)
            )
        }
    }


    private fun startService(code: Int): String {
        ServiceData.sessionServiceList.forEach { e ->
            if (e.code == code) {
                return "service already start at $code"
            }
        }
        val service = SessionBackService(code, sessionAutoService)
        TempData.sessionRun[code] = true
        TempData.sessionCheck[code] = true
        ServiceData.sessionServiceList.add(service)
        val thread = Thread(service, "SessionService$code")
        thread.start()
        return "service Start at $code"
    }

    private fun stopService(code: Int): String {

        var inn = -1
        ServiceData.sessionServiceList.forEachIndexed { index, e ->
            if (e.code == code) {
                TempData.sessionRun[code] = false
                TempData.sessionCheck[code] = false
                inn = index
            }
        }
        if (inn >= 0) ServiceData.sessionServiceList.removeAt(inn)
        return "Service Stop  at $code"

    }
}