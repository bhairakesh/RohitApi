package com.rateApi.rohitApi.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "link_table")
data class LinkTable(

    @Id
    val id: Int,

    val matchLink: String,

    val mStatus: Boolean,

    val sessionLink: String,

    val sStatus: Boolean
)
