package com.rateApi.rohitApi.dao

import com.rateApi.rohitApi.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional


interface UserDao : JpaRepository<UserEntity, Int> {

    fun findByUserName(username: String): UserEntity?


    @Modifying
    @Transactional
    @Query("update UserEntity  u set u.sessiontoken=:token where u.id=:id")
    fun updateToken(id: Int, token: String): Int?


    fun findByStatus(status: Boolean): MutableList<UserEntity>
}