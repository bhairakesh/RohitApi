package com.rateApi.rohitApi

import com.rateApi.rohitApi.backService.MatchBackService
import com.rateApi.rohitApi.backService.SessionBackService


object ServiceData {

    val sessionServiceList: MutableList<SessionBackService> = mutableListOf()
    val matchServiceList: MutableList<MatchBackService> = ArrayList()
}