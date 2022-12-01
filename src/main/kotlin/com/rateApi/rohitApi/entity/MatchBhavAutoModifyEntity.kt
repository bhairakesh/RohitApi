package com.rateApi.rohitApi.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "matchbhavdetails")
data class MatchBhavAutoModifyEntity(

    @Id var id: Int = 0,

    var kRate: Double = 0.0,

    var lRate: Double = 0.0,

    var dateTime: Date = Date(System.currentTimeMillis()),
)
