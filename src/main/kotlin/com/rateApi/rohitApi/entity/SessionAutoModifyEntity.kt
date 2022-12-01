package com.rateApi.rohitApi.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*


@Entity
@Table(name = "sessionbhavdetails")
data class SessionAutoModifyEntity(

    @Id var id: Int = 0,

    var nRun: Int = 0,

    var yRun: Int = 0,

    var nRate: Double = 0.0,

    var yRate: Double = 0.0,

    var dateTime: Date = Date(System.currentTimeMillis()),
)
