package com.poc.social.api.module

import com.poc.social.api.entities.request.FeedCreateRequest
import com.poc.social.api.entities.response.FeedCreateResponse
import com.poc.social.api.entities.response.FeedResponse
import com.poc.social.api.utils.getError
import io.reactivex.Single
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ElasticSearchService {
    private val logger = LoggerFactory.getLogger(ElasticSearchService::class.java)

    @Autowired
    private lateinit var elasticSearch: ElasticSearch

    fun create(request: FeedCreateRequest): Single<FeedCreateResponse> {
        logger.info("Start create with request: $request")

        return elasticSearch.createCard(UUID.randomUUID().toString(), request)
            .doOnSuccess {
                logger.info("End create with response: $it")
            }.doOnError {
                logger.error("Error create with error: ${it.getError()}")
            }
    }

    fun get(id: String): Single<FeedResponse> {
        logger.info("Start get with id: $id")

        return elasticSearch.get(id)
            .doOnSuccess {
                logger.info("End get with response: $it")
            }.doOnError {
                logger.error("Error get with error: ${it.getError()}")
            }
    }
}
