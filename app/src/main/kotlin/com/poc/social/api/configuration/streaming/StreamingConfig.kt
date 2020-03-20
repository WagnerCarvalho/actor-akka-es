package com.poc.social.api.configuration.streaming

import com.fasterxml.jackson.databind.ObjectMapper
import com.poc.social.api.module.streaming.StreamingFeed
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.Streaming
import java.util.concurrent.TimeUnit

@Configuration
class StreamingConfig(
    @Value("\${url_streaming}") val url: String,
    @Value("\${time_out_streaming}") val timeOut: Long
) {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Bean
    fun okHttpClientStreaming(): OkHttpClient =

        OkHttpClient.Builder()
            .connectTimeout(timeOut, TimeUnit.MILLISECONDS)
            .readTimeout(timeOut, TimeUnit.MILLISECONDS)
            .writeTimeout(timeOut, TimeUnit.MILLISECONDS)
            .hostnameVerifier { _, _ -> true }
            .build()

    @Bean
    fun retrofitStreaming(okHttpClientStreaming: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClientStreaming)
            .build()
    }

    @Bean
    fun streaming(retrofitStreaming: Retrofit): StreamingFeed = retrofitStreaming.create(StreamingFeed::class.java)

}