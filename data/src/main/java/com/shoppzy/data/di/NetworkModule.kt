package com.shoppzy.data.di

import com.shoppzy.data.network.NetworkServiceImpl
import com.shoppzy.domain.netwrok.NetworkService
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient(CIO){
            install(ContentNegotiation){
                json(Json{
                  prettyPrint = true
                  isLenient = true
                  ignoreUnknownKeys = true
                })
            }
            install(Logging){
                logger = object : Logger {
                    override fun log(message: String) {
                        println("Ktor log: $message")
                    }
                }
                level = LogLevel.ALL
            }
        }

    }
    single<NetworkService> {
        NetworkServiceImpl(get())
    }
}