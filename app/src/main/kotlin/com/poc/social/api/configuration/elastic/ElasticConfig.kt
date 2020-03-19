package com.poc.social.api.configuration.elastic

import com.fasterxml.jackson.databind.ObjectMapper
import com.poc.social.api.module.ElasticSearch
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

@Configuration
class ElasticConfig(
    @Value("\${url_elastic}") val url: String,
    @Value("\${time_out_elastic}") val timeOut: Long
) {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Bean
    fun okHttpClient(): OkHttpClient =

        OkHttpClient.Builder()
            .connectTimeout(timeOut, TimeUnit.MILLISECONDS)
            .readTimeout(timeOut, TimeUnit.MILLISECONDS)
            .writeTimeout(timeOut, TimeUnit.MILLISECONDS)
            .hostnameVerifier { _, _ -> true }
            .build()

    @Bean
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Bean
    fun elasticSearch(retrofit: Retrofit): ElasticSearch = retrofit.create(ElasticSearch::class.java)

}