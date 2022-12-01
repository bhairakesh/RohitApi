package com.rateApi.rohitApi.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.intellij.lang.annotations.Pattern
import java.util.*


@Entity
@Table(name = "sessionbhavdetails")
data class SessionBhavAutoEntity(

    @Id val id: Int? = null,

    val code: Int = 0,

    var name: String = "",

    var nRun: Int = 0,

    var yRun: Int = 0,

    var nRate: Double = 0.0,

    var yRate: Double = 0.0,

    var sid: String = "",

    val lockStatus: Boolean = true,

    var empLockStatus: Boolean = false,

    var dateTime: Date = Date(System.currentTimeMillis()),

    var position: Int = 0,

    var type: Boolean = false,

    var slimit: Int = 0,

    var userId: Int = 0,

    var sName: String? = null
)
