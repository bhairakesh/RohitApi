package com.rateApi.rohitApi.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "matchbhavdetails")
data class MatchBhavAutoEntity(

    @Id
    val id: Int = 0,

    val code: Int = 0,

    val team: String = "",

    val kRate: Double = 0.0,

    val lRate: Double = 0.0,

    val lockStatus: Boolean = true,

    val empLockStatus: Boolean = true,

    var teamId: Int = 0,

    val kid: String = "",

    val type: Boolean = false,
)
