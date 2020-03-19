package com.poc.social.api.configuration.akka

import akka.actor.ActorSystem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AkkaConfig {
    @Autowired
    private val applicationContext: ApplicationContext? = null

    @Bean
    fun actorSystem(): ActorSystem {
        val actorSystem = ActorSystem.create("ActorSystem")
        SpringExtension.SpringExtProvider[actorSystem]!!.initialize(applicationContext)
        return actorSystem
    }
}