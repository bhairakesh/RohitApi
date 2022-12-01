package com.rateApi.rohitApi.controller

import com.rateApi.rohitApi.TempData
import com.rateApi.rohitApi.dao.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/automation")
class AutomaticRateController {

    @Autowired
    lateinit var userDao: UserDao

    @GetMapping("/{code}/{gid}")
    fun index(@PathVariable code: Int, @PathVariable gid: String, m: Model): String {
        m.addAttribute("code", code)
        m.addAttribute("gameId", gid)
        m.addAttribute("users", userDao.findByStatus(true))
        TempData.gid[code] = gid
        return "crick"
    }

}