package com.rateApi.rohitApi.entity

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "matchbhavdetails")
data class MatchBhavEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    var code: Int = 0,

    var kRate: Double = 0.0,

    var lRate: Double = 0.0,

    var team: String = "",

    val lockStatus: Boolean = true,

    val empLockStatus: Boolean = true,

    var dateTime: Date = Date(System.currentTimeMillis()),

    var teamId: Int = 0,

    val kid: String = "",

    val type: Boolean = false,

    var userId: Int = 0
)
