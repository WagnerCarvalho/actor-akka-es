package com.poc.social.api.service

import com.poc.social.api.entities.request.FeedCreateRequest
import com.poc.social.api.entities.response.FeedCreateResponse
import com.poc.social.api.entities.response.FeedResponse
import com.poc.social.api.module.ElasticSearchService
import com.poc.social.api.utils.getError
import io.reactivex.Single
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FeedService {
    private val logger = LoggerFactory.getLogger(FeedService::class.java)

    @Autowired
    private lateinit var elasticSearchService: ElasticSearchService

    fun create(request: FeedCreateRequest): Single<FeedCreateResponse> {
        logger.info("Start create with request: $request")

        return elasticSearchService.create(request)
            .doOnSuccess {
                logger.info("end create with response: $it")
            }.doOnError {
                logger.error("Error create with error: ${it.getError()}")
            }
    }

    fun get(id: String): Single<FeedResponse> {
        logger.info("Start get with id: $id")

        return elasticSearchService.get(id)
            .doOnSuccess {
                logger.info("End get with response: $it")
            }.doOnError {
                logger.error("Error get with error: ${it.getError()}")
            }
    }
}
