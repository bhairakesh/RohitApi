package com.rateApi.rohitApi.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "sessionbhavdetails")
data class SessionBhavAutoEntity(

    @Id
    val id: Int,

    val code: Int,

    val name: String = "",

    var nRun: Int = 0,

    var yRun: Int = 0,

    var nRate: Double = 0.0,

    var yRate: Double = 0.0,

    var sid: String = "",

    val lockStatus: Boolean = true,

    val empLockStatus: Boolean = true,

    var position: Int = 0,

    val type: Boolean = false,
)
