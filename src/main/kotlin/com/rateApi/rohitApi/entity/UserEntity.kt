package com.rateApi.rohitApi.entity

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "userdetails")
data class UserEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    var name: String = "",

    var userName: String = "",

    var address: String = "",

    var contactNo: String = "",

    var role: String = "",

    var password: String = "",

    var status: Boolean = true,

    var sessiontoken: String? = "",

    val createdAt: Date = Date(System.currentTimeMillis()),
)
