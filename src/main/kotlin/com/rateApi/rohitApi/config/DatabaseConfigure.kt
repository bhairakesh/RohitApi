package com.rateApi.rohitApi.config

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DatabaseConfigure {
    @Bean
    fun dataSource(): DataSource {
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.url("jdbc:mysql://private-db.cluster-ckqvlnvird83.ap-south-1.rds.amazonaws.com:3306/odds")
        dataSourceBuilder.username("player11")
        dataSourceBuilder.password("9XU}5CXL]i*Ugh:JCJqW")
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver")
        return dataSourceBuilder.build()
    }
}
