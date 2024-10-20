package com.rpgportugal.orthanc.kt.dependencies

import arrow.core.Either
import com.rpgportugal.orthanc.kt.configuration.PropertiesLoader
import com.rpgportugal.orthanc.kt.error.DatabaseError
import com.rpgportugal.orthanc.kt.error.NullInputStreamError
import com.rpgportugal.orthanc.kt.error.ThrowableError
import com.rpgportugal.orthanc.kt.persistence.repository.application.ApplicationRepository
import com.rpgportugal.orthanc.kt.persistence.repository.application.db.SqlApplicationRepository
import org.koin.dsl.bind
import org.koin.dsl.module
import org.ktorm.database.Database

object DbModule : DepModule{
    override val module = module {
        factory {
            val propertiesLoader = get<PropertiesLoader>()

            val dbProperties = when (val result = propertiesLoader.load("secret/database.properties")) {
                is Either.Right -> result.value
                is Either.Left -> when(val error = result.value) {
                    is ThrowableError<*> -> throw error.exception
                    is NullInputStreamError -> throw Exception("${error.fileName} not found - ${error.message}")
                }
            }

            val url = dbProperties.getProperty("url") ?: throw Exception("Missing url property from database.properties")

            Database.connect(url=url)
        }
        factory { SqlApplicationRepository(get()) } bind ApplicationRepository::class
    }
}